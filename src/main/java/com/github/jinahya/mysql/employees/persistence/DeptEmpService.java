package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.List;

public interface DeptEmpService
        extends _BaseEntityService<DeptEmp> {

    /**
     * Returns current assignment of specified employee.
     *
     * @param employee the employee whose current assignment is found.
     * @return alist of current assignment.
     */
    List<DeptEmp> getCurrentAssignments(@Nonnull Employee employee);

    /**
     * Assigns specified employee to specified department.
     *
     * @param employee   the employ to be assigned to the {@code department}.
     * @param department the department to which the {@code employee} is assigned.
     */
    void assign(@Nonnull Employee employee, @Nonnull Department department, @Nonnull LocalDate fromDate);

    /**
     * Assigns specified employee to specified department.
     *
     * @param employee   the employ to be assigned to the {@code department}.
     * @param department the department to which the {@code employee} is assigned.
     */
    void unassign(@Nonnull Employee employee, @Nonnull Department department);
}
