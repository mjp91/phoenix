package com.mpearsall.hr.controller;

import com.mpearsall.hr.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/resource")
public class FileUploadController {
  private final StorageService storageService;

  @Autowired
  public FileUploadController(StorageService storageService) {
    this.storageService = storageService;
  }

  @GetMapping("/files/{fileName:.+}")
  public ResponseEntity<Resource> serve(@PathVariable String fileName) {
    final Resource file = storageService.loadAsResource(fileName);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @PostMapping(path = "")
  public String upload(@RequestParam("file") MultipartFile file) {
    return storageService.store(file);
  }
}
