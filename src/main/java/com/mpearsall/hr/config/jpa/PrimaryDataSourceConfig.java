package com.mpearsall.hr.config.jpa;

import com.mpearsall.hr.util.DataSourceUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class PrimaryDataSourceConfig {
  @Value("${spring.datasource.url}")
  private String defaultUrl;

  @Value("${spring.datasource.data-username}")
  private String defaultUsername;

  @Value("${spring.datasource.data-password}")
  private String defaultPassword;

  @Primary
  @Bean(name = "dataSource")
  public DataSource dataSource() {
    return DataSourceUtil.buildDataSource(defaultUrl, defaultUsername, defaultPassword);
  }
}
