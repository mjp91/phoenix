package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayDate;
import com.mpearsall.hr.entity.holiday.HolidayPeriod;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayService {
  public Double calculateHolidayUsed(Employee employee, HolidayYear holidayYear) {
    final List<HolidayDate> holidayDates = employee.getHolidays().stream()
        .filter(holiday -> holiday.getHolidayYear().equals(holidayYear))
        .filter(Holiday::isApproved)
        .map(Holiday::getHolidayDates)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    double holidayUsed = 0.0;
    for (HolidayDate holidayDate : holidayDates) {
      if (holidayDate.getHolidayPeriod() == HolidayPeriod.ALL_DAY) {
        holidayUsed += 1.0;
      } else {
        holidayUsed += 0.5;
      }
    }

    return holidayUsed;
  }
}
