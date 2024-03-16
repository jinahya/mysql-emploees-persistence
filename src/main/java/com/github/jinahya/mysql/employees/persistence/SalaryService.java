package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public interface SalaryService extends _BaseEntityService<Salary> {

    void set(@Valid @NotNull Employee employee, @Positive int salary, @NotNull LocalDate fromDate,
             @Nullable LocalDate toDate);
}
