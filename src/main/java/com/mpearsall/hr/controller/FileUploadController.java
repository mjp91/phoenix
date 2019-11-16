package com.mpearsall.hr.controller;

import com.mpearsall.hr.exception.StorageFileNotFoundException;
import com.mpearsall.hr.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/resource")
public class FileUploadController {
  private final StorageService storageService;

  private final ResourceLoader resourceLoader;

  @Autowired
  public FileUploadController(StorageService storageService, ResourceLoader resourceLoader) {
    this.storageService = storageService;
    this.resourceLoader = resourceLoader;
  }

  @GetMapping("/{fileName:.+}")
  public ResponseEntity<Resource> serve(@PathVariable String fileName) {
    final Resource file = storageService.loadAsResource(fileName);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
        .body(file);
  }

  @GetMapping("/image/{fileName:.+}")
  public ResponseEntity<Resource> serveImage(@PathVariable String fileName) {
    Resource file;
    try {
      file = storageService.loadAsResource(fileName);
    } catch (StorageFileNotFoundException e) {
      file = resourceLoader.getResource("classpath:image/" + fileName);

      if (!file.exists() || !file.isReadable()) {
        file = resourceLoader.getResource("classpath:image/default.png");
      }
    }

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<String> upload(@RequestParam("file") MultipartFile[] files) {
    final List<String> fileNames = new ArrayList<>();

    for (MultipartFile file : files) {
      fileNames.add(storageService.store(file));
    }

    return fileNames;
  }
}
