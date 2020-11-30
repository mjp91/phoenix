package com.mpearsall.hr.config.jpa;

import com.mpearsall.hr.util.DataSourceUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SecondaryDataSourceConfig {
  @Value("${hr.datasource.url}")
  private String defaultUrl;

  @Value("${hr.datasource.default-db-name}")
  private String defaultDbName;

  @Value("${hr.datasource.username}")
  private String defaultUsername;

  @Value("${hr.datasource.password}")
  private String defaultPassword;

  @Bean(name = "secondaryDataSource")
  public DataSource dataSource() {
    return DataSourceUtil.buildDataSource(String.format("%s/%s", defaultUrl, defaultDbName), defaultUsername, defaultPassword);
  }
}
