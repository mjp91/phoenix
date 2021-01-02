package com.mpearsall.hr.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
  String store(MultipartFile file);

  Path load(String fileName);

  Resource loadAsResource(String fileName);

  void delete(String fileName);
}
