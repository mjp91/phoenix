package com.mpearsall.hr.config.tenancy;

import com.mpearsall.hr.entity.primary.client.Client;
import com.mpearsall.hr.repository.primary.ClientRepository;
import com.mpearsall.hr.util.DataSourceUtil;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements ApplicationListener<ApplicationReadyEvent> {
  public static final String DEFAULT_TENANT_ID = "default";

  private final ClientRepository clientRepository;

  private final DataSource defaultDataSource;

  private Map<String, DataSource> dataSources;

  public DataSourceBasedMultiTenantConnectionProviderImpl(ClientRepository clientRepository, @Qualifier("secondaryDataSource") DataSource defaultDataSource) {
    this.clientRepository = clientRepository;
    this.defaultDataSource = defaultDataSource;
  }

  @PostConstruct
  void init() {
    dataSources = new HashMap<>();

    dataSources.put(DEFAULT_TENANT_ID, defaultDataSource);
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    refresh();
  }

  public void refresh() {
    final Map<String, DataSource> newDataSources = new HashMap<>();
    newDataSources.put(DEFAULT_TENANT_ID, defaultDataSource);

    // source from old map first to avoid creating new DataSources
    for (Client client : clientRepository.findAll()) {
      final String clientName = client.getName();
      if (dataSources.containsKey(clientName)) {
        newDataSources.put(clientName, dataSources.get(clientName));
      } else {
        dataSources.put(clientName, DataSourceUtil.buildDataSource(client));
      }
    }

    dataSources = newDataSources;
  }

  @Override
  protected DataSource selectAnyDataSource() {
    return dataSources.get(DEFAULT_TENANT_ID);
  }

  @Override
  protected DataSource selectDataSource(String s) {
    return dataSources.get(s);
  }

  public Map<String, DataSource> getDataSources() {
    return dataSources;
  }
}
