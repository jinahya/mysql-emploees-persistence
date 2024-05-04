package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Salary;
import com.github.jinahya.mysql.employees.persistence.SalaryId;

abstract class SalaryRepository_Test
        extends _BaseEntityRepositoryTest<SalaryRepository, Salary, SalaryId> {

    SalaryRepository_Test() {
        super(SalaryRepository.class);
    }
}