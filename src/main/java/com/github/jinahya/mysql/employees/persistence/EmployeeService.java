package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.LockModeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

class EmployeeService extends _BaseService<Employee, Integer> {

    EmployeeService() {
        super(Employee.class);
    }

    boolean setNewSalary(final @Valid @NotNull Employee employee, final @Positive int salary,
                         final @NotNull LocalDate fromDate) {
        applyEntityManagerInTransaction(em -> {
            em.lock(employee, LockModeType.PESSIMISTIC_WRITE);
            return null;
        });
        // TODO: implement!
        return false;
    }

    boolean setNewSalary(final @Valid @NotNull int empNo, final @Positive int salary,
                         final @NotNull LocalDate fromDate) {
        applyEntityManagerInTransaction(em -> {
            return null;
        });
        // TODO: implement!
        return false;
    }
}
