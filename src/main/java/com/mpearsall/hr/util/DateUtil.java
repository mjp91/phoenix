package com.mpearsall.hr.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtil {
  private DateUtil() {
  }

  public static Date convertToDateViaInstant(LocalDate dateToConvert) {
    return java.util.Date.from(dateToConvert.atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant());
  }

  public static net.fortuna.ical4j.model.Date convertToIcalDateViaInstant(LocalDate dateToConvert) {
    return new net.fortuna.ical4j.model.Date(java.util.Date.from(dateToConvert.atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant()));
  }
}
