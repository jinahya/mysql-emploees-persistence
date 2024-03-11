package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Department;

class DepartmentRepository_IT extends _BaseEntityRepositoryIT<DepartmentRepository, Department, String> {

    DepartmentRepository_IT() {
        super(DepartmentRepository.class);
    }
}
