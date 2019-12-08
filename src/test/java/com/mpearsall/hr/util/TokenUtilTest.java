package com.mpearsall.hr.util;

import org.junit.Assert;
import org.junit.Test;

public class TokenUtilTest {

  @Test
  public void randomToken() {
    for (int i = 1; i <= 512; i++) {
      final String token = TokenUtil.randomToken(i);
      Assert.assertEquals(i, token.length());
    }
  }
}
