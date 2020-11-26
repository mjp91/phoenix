package com.mpearsall.hr.config;

import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.service.EmployeeService;
import com.mpearsall.hr.view.View;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class SecurityJsonViewControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {
  private final EmployeeService employeeService;

  public SecurityJsonViewControllerAdvice(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @Override
  protected void beforeBodyWriteInternal(MappingJacksonValue mappingJacksonValue, MediaType mediaType,
                                         MethodParameter methodParameter, ServerHttpRequest serverHttpRequest,
                                         ServerHttpResponse serverHttpResponse) {
    // should any views be applied for this value?
    if (isExempt(mappingJacksonValue)) return;

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.getAuthorities() != null) {
      final Collection<? extends GrantedAuthority> authorities
          = authentication.getAuthorities();

      final List<Class<?>> jsonViews = authorities.stream()
          .map(GrantedAuthority::getAuthority)
          .map(View.MAPPINGS::get)
          .collect(Collectors.toList());

      if (jsonViews.size() != 1) {
        throw new IllegalArgumentException("Ambiguous @JsonView declaration for roles " +
            authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
      }

      mappingJacksonValue.setSerializationView(jsonViews.get(0));
    }
  }

  private boolean isExempt(MappingJacksonValue mappingJacksonValue) {
    final Object value = mappingJacksonValue.getValue();

    if (value instanceof Employee) {
      // if viewing own employee record
      return employeeService.getCurrentUserEmployee().equals(value);
    }

    return false;
  }
}
