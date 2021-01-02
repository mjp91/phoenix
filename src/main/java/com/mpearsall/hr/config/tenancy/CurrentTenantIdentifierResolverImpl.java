package com.mpearsall.hr.config.tenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
  @Override
  public String resolveCurrentTenantIdentifier() {
    return TenantContext.getTenantId();
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
