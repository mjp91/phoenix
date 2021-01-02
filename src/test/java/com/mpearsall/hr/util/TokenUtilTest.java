package com.mpearsall.hr.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenUtilTest {

  @Test
  public void randomToken() {
    for (int i = 1; i <= 512; i++) {
      final String token = TokenUtil.randomToken(i);
      Assertions.assertEquals(i, token.length());
    }
  }
}
