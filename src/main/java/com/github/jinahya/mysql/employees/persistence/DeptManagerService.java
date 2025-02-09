package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public interface DeptManagerService
        extends _BaseEntityService<DeptManager> {

    /**
     * Appoints specified employee as the manager of specified department, as of specified date.
     *
     * @param employee   the employee to be appointed.
     * @param department the department to which the {@code employee} is appointed as the manager.
     * @param fromDate   the date of appointment.
     */
    void appoint(@Nonnull Employee employee, @Nonnull Department department, @Nonnull LocalDate fromDate);
}
