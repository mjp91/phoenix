package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.License;
import com.mpearsall.hr.entity.Config;
import com.mpearsall.hr.repository.ConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@Slf4j
public class LicenseService {
  @Value("${hr.license.url:#{null}}")
  private String licenseUrl;

  private final ConfigRepository configRepository;

  private final RestTemplate restTemplate;
  private License license = null;

  public LicenseService(ConfigRepository configRepository) {
    this.configRepository = configRepository;
    restTemplate = new RestTemplate();
  }

  @PostConstruct
  void init() {
    if (licenseUrl == null) {
      throw new IllegalStateException("hr.license.url is null");
    }

    try {
      updateLicense();
    } catch (RestClientException e) {
      log.error("Unable to obtain license during init", e);
    }
  }

  public void updateLicense() {
    final Config config = configRepository.find();
    final String licenseAuthenticationToken = config.getLicenseAuthenticationToken();

    if (licenseAuthenticationToken != null) {
      final HttpHeaders headers = new HttpHeaders();
      headers.add("X-Auth-Token", licenseAuthenticationToken);
      headers.setContentType(MediaType.APPLICATION_JSON);

      final HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

      license = restTemplate.postForObject(licenseUrl, request, License.class);
    }
  }

  public License getLicense() {
    return license;
  }
}
