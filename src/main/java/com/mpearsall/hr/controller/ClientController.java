package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.ClientDto;
import com.mpearsall.hr.dto.DtoMapper;
import com.mpearsall.hr.dto.NewClient;
import com.mpearsall.hr.service.ClientService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientController {
  private final ClientService clientService;
  private final DtoMapper dtoMapper;

  public ClientController(ClientService clientService, DtoMapper dtoMapper) {
    this.clientService = clientService;
    this.dtoMapper = dtoMapper;
  }

  @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ClientDto> newClient(@RequestBody NewClient newClient) {
    final ClientDto client = dtoMapper.toClientDto(clientService.create(newClient));

    return ResponseEntity.ok(client);
  }
}
