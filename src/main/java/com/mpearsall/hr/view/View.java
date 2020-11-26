package com.mpearsall.hr.view;

import com.mpearsall.hr.entity.primary.user.Role;

import java.util.HashMap;
import java.util.Map;

public class View {
  public static final Map<String, Class<?>> MAPPINGS = new HashMap<>();

  static {
    MAPPINGS.put(Role.ADMIN, Admin.class);
    MAPPINGS.put(Role.USER, User.class);
  }

  public static class User {
  }

  public static class Admin extends User {
  }
}
