package com.mpearsall.hr.config;

import com.mpearsall.hr.config.tenancy.DataSourceBasedMultiTenantConnectionProviderImpl;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class FlywaySecondaryConfig {

  private final DataSourceBasedMultiTenantConnectionProviderImpl multiTenantConnectionProvider;

  public FlywaySecondaryConfig(DataSourceBasedMultiTenantConnectionProviderImpl multiTenantConnectionProvider) {
    this.multiTenantConnectionProvider = multiTenantConnectionProvider;
  }

  @PostConstruct
  void init() {
    FluentConfiguration configuration = Flyway.configure()
        .locations("classpath:db/migration/secondary");

    for (DataSource dataSource : multiTenantConnectionProvider.getDataSources().values()) {
      configuration = configuration.dataSource(dataSource);
      new Flyway(configuration).migrate();
    }
  }
}
