package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Salary;
import com.github.jinahya.mysql.employees.persistence.SalaryId;

abstract class SalaryRepository__IT extends _BaseEntityRepositoryIT<SalaryRepository, Salary, SalaryId> {

    SalaryRepository__IT() {
        super(SalaryRepository.class);
    }
}