package com.github.jinahya.mysql.employees.persistence.service;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Salary;
import com.github.jinahya.mysql.employees.persistence.SalaryId;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public interface SalaryService
        extends _IBaseEntityService<Salary, SalaryId> {

    void set(@Valid @NotNull Employee employee, @Positive int salary, @NotNull LocalDate fromDate,
             @Nullable LocalDate toDate);
}
