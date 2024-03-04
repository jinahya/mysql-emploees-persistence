package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Department;

class DepartmentRepository_Test extends _BaseEntityRepositoryTest<DepartmentRepository, Department, String> {

    DepartmentRepository_Test() {
        super(DepartmentRepository.class);
    }
}
