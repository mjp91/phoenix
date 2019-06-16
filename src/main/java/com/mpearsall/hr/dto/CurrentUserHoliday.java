package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CurrentUserHoliday implements Serializable {
  private final double used;
  private final double total;

  public double getRemaining() {
    return total - used;
  }
}
