package com.mpearsall.hr.util;

import java.security.SecureRandom;
import java.util.Base64;

public final class TokenUtil {
  private static SecureRandom random = new SecureRandom();
  private static Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

  private TokenUtil() {
  }

  public static String randomToken(int length) {
    int byteLength = (int) Math.ceil((length * 6.0) / 8.0);

    final byte[] bytes = new byte[byteLength];
    random.nextBytes(bytes);

    return encoder.encodeToString(bytes).substring(0, length);
  }
}
