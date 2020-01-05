package com.mpearsall.hr;

import com.mpearsall.hr.entity.holiday.CompanyYear;
import com.mpearsall.hr.factory.CompanyYearFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public abstract class HrApplicationTests {
  @Autowired
  private CompanyYearFactory companyYearFactory;

  protected CompanyYear companyYear;

  @Before
  public void setUp() {
    companyYear = companyYearFactory.generateForCurrentYear();
  }
}
