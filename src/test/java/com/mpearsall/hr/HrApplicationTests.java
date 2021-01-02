package com.mpearsall.hr;

import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.factory.CompanyYearFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public abstract class HrApplicationTests {
  @Autowired
  private CompanyYearFactory companyYearFactory;

  protected CompanyYear companyYear;

  @BeforeEach
  public void setUp() {
    companyYear = companyYearFactory.generateForCurrentYear();
  }
}
