package com.mpearsall.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotpRegister implements Serializable {
  private String totpUrl;
  private int verificationCode;
  private List<Integer> scratchCodes;
}
