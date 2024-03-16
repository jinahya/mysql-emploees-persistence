package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Salary;
import com.github.jinahya.mysql.employees.persistence.SalaryId;

abstract class SalaryRepositoryTest extends _BaseEntityRepositoryTest<SalaryRepository, Salary, SalaryId> {

    SalaryRepositoryTest() {
        super(SalaryRepository.class);
    }
}