package com.mpearsall.hr.config.tenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantContext {
  private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

  private static final Logger LOGGER = LoggerFactory.getLogger(TenantContext.class);

  public static void setTenantId(String tenantId) {
    LOGGER.debug("Setting tenant id: {}", tenantId);
    CONTEXT.set(tenantId);
  }

  public static String getTenantId() {
    String tenantId = CONTEXT.get();

    if (tenantId == null) {
      tenantId = DataSourceBasedMultiTenantConnectionProviderImpl.DEFAULT_TENANT_ID;
    }

    return tenantId;
  }
}
