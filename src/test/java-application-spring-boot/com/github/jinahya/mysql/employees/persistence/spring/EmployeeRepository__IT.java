package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;

abstract class EmployeeRepository__IT
        extends _BaseEntityRepositoryIT<EmployeeRepository, Employee, Integer> {

    // -----------------------------------------------------------------------------------------------------------------
    EmployeeRepository__IT() {
        super(EmployeeRepository.class);
    }
}
