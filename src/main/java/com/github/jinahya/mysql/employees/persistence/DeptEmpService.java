package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.Optional;

public interface DeptEmpService
        extends _BaseEntityService<DeptEmp> {

    /**
     * Returns current assignment of specified employee.
     *
     * @param employee the employee whose current assignment is found.
     * @return an optional of current assignement.
     */
    Optional<DeptEmp> current(@Nonnull Employee employee);

    /**
     * Assigns specified employee to specified department.
     *
     * @param employee   the employ to be assigned to the {@code department}.
     * @param department the department to which the {@code employee} is assigned.
     */
    void assign(@Nonnull Employee employee, @Nonnull Department department, @Nonnull LocalDate fromDate);
}
