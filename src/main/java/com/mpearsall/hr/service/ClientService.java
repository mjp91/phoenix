package com.mpearsall.hr.service;

import com.mpearsall.hr.config.tenancy.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.mpearsall.hr.config.tenancy.TenantContext;
import com.mpearsall.hr.dto.CreateUser;
import com.mpearsall.hr.dto.NewClient;
import com.mpearsall.hr.entity.primary.client.Client;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.secondary.Company;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.repository.primary.ClientRepository;
import com.mpearsall.hr.repository.primary.UserRepository;
import com.mpearsall.hr.repository.secondary.CompanyRepository;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class ClientService {
  private final ClientRepository clientRepository;
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;
  private final UserService userService;
  private final DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProvider;

  @Value("${hr.datasource.url}")
  private String defaultUrl;

  @Value("${hr.datasource.username}")
  private String username;

  @Value("${hr.datasource.password}")
  private String password;


  public ClientService(ClientRepository clientRepository, UserRepository userRepository, CompanyRepository companyRepository,
                       UserService userService, DataSourceBasedMultiTenantConnectionProviderImpl dataSourceBasedMultiTenantConnectionProvider) {
    this.clientRepository = clientRepository;
    this.userRepository = userRepository;
    this.companyRepository = companyRepository;
    this.userService = userService;
    this.dataSourceBasedMultiTenantConnectionProvider = dataSourceBasedMultiTenantConnectionProvider;
  }

  @Transactional
  public Client create(NewClient newClient) {
    final String name = newClient.getName();
    final CreateUser adminUser = newClient.getUser();

    // validate
    if (clientRepository.existsByName(name)) {
      throw new InvalidDetailsException("Client already exists");
    }

    if (userRepository.existsByUsername(adminUser.getUsername())) {
      throw new InvalidDetailsException("User already exists");
    }

    // create new client
    Client client = new Client();
    client.setName(name);
    client.setExpiry(newClient.getExpiry());
    client.setDatabaseUser(username);
    client.setDatabasePassword(password);

    // create database
    final DataSource dataSource = dataSourceBasedMultiTenantConnectionProvider.getDataSources()
        .get(DataSourceBasedMultiTenantConnectionProviderImpl.DEFAULT_TENANT_ID);

    // remove invalid chars
    String dbName = name
        .replaceAll("\\s", "_")
        .replaceAll("[^a-zA-Z0-9_]", "")
        .toLowerCase();

    // max length of 63
    if (dbName.length() > 63) {
      dbName = dbName.substring(0, 63);
    }

    // create the database
    createDatabase(dataSource, dbName);

    // set JDBC URL
    client.setJdbcUrl(String.format("%s/%s", defaultUrl, dbName));
    client = clientRepository.save(client);

    // add to tenant provider
    dataSourceBasedMultiTenantConnectionProvider.refresh();

    // migrate
    migrate(dataSourceBasedMultiTenantConnectionProvider.getDataSources().get(client.getName()));

    // record tenant id to revert back to
    final String currentTenantId = TenantContext.getTenantId();
    try {
      // switch to new client tenant
      TenantContext.setTenantId(client.getName());

      // create company
      final Company company = new Company();
      company.setName(name);
      companyRepository.save(company);

      // client admin user
      userService.createUser(adminUser, client, Collections.singletonList(Role.ADMIN));
    } finally {
      // revert back
      TenantContext.setTenantId(currentTenantId);
    }

    return client;
  }

  private void createDatabase(DataSource dataSource, String dbName) {
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    final String sql = String.format("CREATE DATABASE \"%s\"", dbName);
    jdbcTemplate.execute(sql);
  }

  private void migrate(DataSource dataSource) {
    // run migrations
    final FluentConfiguration configuration = Flyway.configure()
        .locations("classpath:db/migration/secondary")
        .dataSource(dataSource);

    new Flyway(configuration).migrate();
  }


}
