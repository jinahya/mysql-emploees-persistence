package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;

class EmployeeRepositoryTest extends _BaseEntityRepositoryTest<EmployeeRepository, Employee, Integer> {

    EmployeeRepositoryTest() {
        super(EmployeeRepository.class);
    }
}
