package com.mpearsall.hr.util;

import com.mpearsall.hr.entity.primary.client.Client;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public final class DataSourceUtil {
  private DataSourceUtil() {
  }

  public static DataSource buildDataSource(Client client) {
    return DataSourceBuilder.create()
        .url(client.getJdbcUrl())
        .username(client.getDatabaseUser())
        .password(client.getDatabasePassword())
        .build();
  }

  public static DataSource buildDataSource(String url, String username, String password) {
    return DataSourceBuilder.create()
        .url(url)
        .username(username)
        .password(password)
        .build();
  }
}
