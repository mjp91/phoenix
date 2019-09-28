package com.mpearsall.hr.service.storage;

import com.mpearsall.hr.exception.StorageException;
import com.mpearsall.hr.exception.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {
  private static final String FILE_NOT_FOUND_MSG = "File not found";

  private final Path rootLocation;

  @Autowired
  public FileSystemStorageService(StorageProperties properties) {
    this.rootLocation = Paths.get(properties.getLocation());
  }

  @Override
  public String store(MultipartFile file) {
    final String filename = generateNewFileName(file.getOriginalFilename());

    if (file.isEmpty()) {
      throw new StorageException("File is empty");
    }

    try (InputStream is = file.getInputStream()) {
      Files.copy(is, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new StorageException("Failed to store file", e);
    }

    return filename;
  }

  @Override
  public Path load(String fileName) {
    return this.rootLocation.resolve(fileName);
  }

  @Override
  public Resource loadAsResource(String fileName) {
    try {
      Resource resource = new UrlResource(load(fileName).toUri());

      if (resource.exists() && resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(FILE_NOT_FOUND_MSG);
      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException(FILE_NOT_FOUND_MSG, e);
    }
  }

  @Override
  public void delete(String fileName) {
    try {
      Files.delete(load(fileName));
    } catch (IOException e) {
      throw new StorageException("Failed to delete", e);
    }
  }

  private String generateNewFileName(String originalFileName) {
    originalFileName = StringUtils.cleanPath(originalFileName);

    final int dotIndex = originalFileName.indexOf('.');

    String extension = "";
    if (dotIndex != -1) {
      extension = originalFileName.substring(dotIndex);
    }

    return UUID.randomUUID().toString() + extension;
  }
}
