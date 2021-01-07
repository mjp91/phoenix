package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.primary.client.Client;
import com.mpearsall.hr.entity.primary.user.Role;
import com.mpearsall.hr.entity.secondary.Attachment;
import com.mpearsall.hr.entity.secondary.Department;
import com.mpearsall.hr.entity.secondary.absence.Absence;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.employee.JobRole;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.entity.secondary.holiday.Holiday;
import com.mpearsall.hr.entity.secondary.holiday.HolidayDate;
import com.mpearsall.hr.entity.secondary.holiday.HolidayEntitlement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MappingQualifier.class, UserDtoMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DtoMapper {
  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  AbsenceDto toAbsenceDto(Absence absence);

  Absence toAbsence(AbsenceDto absenceDto);

  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  AttachmentDto toAttachmentDto(Attachment attachment);

  Attachment toAttachment(AttachmentDto attachmentDto);

  @Mapping(target = "user", qualifiedByName = "userIdToUser")
  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  EmployeeDto toEmployeeDto(Employee employee);

  @Mapping(target = "user", qualifiedByName = "userToUserId")
  Employee toEmployee(EmployeeDto employeeDto);

  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  DepartmentDto toDepartmentDto(Department department);

  Department toDepartment(DepartmentDto departmentDto);

  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  JobRoleDto toJobRoleDto(JobRole jobRole);

  JobRole toJobRole(JobRoleDto jobRoleDto);

  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  CompanyYearDto toCompanyYearDto(CompanyYear companyYear);

  @Mapping(target = "id")
  CompanyYear toCompanyYear(CompanyYearDto companyYearDto);

  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  HolidayDto toHolidayDto(Holiday holiday);

  Holiday toHoliday(HolidayDto holidayDto);

  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  HolidayDateDto toHolidayDateDto(HolidayDate holidayDate);

  HolidayDate toHolidayDate(HolidayDateDto holidayDateDto);

  @Mapping(target = "createdDate", qualifiedByName = "unwrap")
  HolidayEntitlementDto toHolidayEntitlementDto(HolidayEntitlement holidayEntitlement);

  HolidayEntitlement toHolidayEntitlement(HolidayEntitlementDto holidayEntitlementDto);

  Iterable<HolidayEntitlementDto> toHolidayEntitlementDtos(Iterable<HolidayEntitlement> holidayEntitlements);

  RoleDto toRoleDto(Role role);

  Role toRole(RoleDto roleDto);

  ClientDto toClientDto(Client client);
}
