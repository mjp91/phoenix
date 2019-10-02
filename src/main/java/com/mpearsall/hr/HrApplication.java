package com.mpearsall.hr;

import com.mpearsall.hr.config.security.SpringSecurityAuditorAware;
import com.mpearsall.hr.entity.user.User;
import com.mpearsall.hr.service.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(StorageProperties.class)
public class HrApplication {

  public static void main(String[] args) {
    SpringApplication.run(HrApplication.class, args);
  }

  @Bean
  public AuditorAware<User> auditorProvider() {
    return new SpringSecurityAuditorAware();
  }
}
