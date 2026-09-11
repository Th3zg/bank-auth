package com.bank.auth.auth_services.enums;

public enum RoleCode {

  CUSTOMER_BASIC("CUSTOMER_BASIC"),
  EMPLOYEE_TELLER("EMPLOYEE_TELLER"),
  EMPLOYEE_MANAGER("EMPLOYEE_MANAGER"),
  EMPLOYEE_SUPPORT("EMPLOYEE_SUPPORT"),
  EMPLOYEE_AUDITOR("EMPLOYEE_AUDITOR"),
  EMPLOYEE_ANALYST("EMPLOYEE_ANALYST"),
  EMPLOYEE_SUPERVISOR("EMPLOYEE_SUPERVISOR"),
  EMPLOYEE_COMPLIANCE("EMPLOYEE_COMPLIANCE"),
  SYSTEM_ADMIN("SYSTEM_ADMIN");

  private final String value;

  RoleCode(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
