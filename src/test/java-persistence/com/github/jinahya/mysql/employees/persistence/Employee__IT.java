package com.github.jinahya.mysql.employees.persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Employee__IT
        extends _BaseEntityIT<Employee, Integer> {

    // -----------------------------------------------------------------------------------------------------------------
    Employee__IT() {
        super(Employee.class);
    }
}
