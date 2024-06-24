package com.github.jinahya.mysql.employees.persistence.service;

/*-
 * #%L
 * mysql-employees-persistece
 * %%
 * Copyright (C) 2024 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.DeptEmp;
import com.github.jinahya.mysql.employees.persistence.DeptEmpId;
import com.github.jinahya.mysql.employees.persistence.Employee;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public interface IDeptEmpService
        extends _IBaseEntityService<DeptEmp, DeptEmpId> {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns assignments of specified employee based on specified date.
     *
     * @param employee the employee whose current assignments are found.
     * @param date     the base date.
     * @return a list of assignments of {@code employee} on {@code date}.
     */
    @NotNull
    List<@Valid @NotNull DeptEmp> getAssignments(@Valid @NotNull @Nonnull Employee employee,
                                                 @NotNull @Nonnull LocalDate date);

    /**
     * Returns {@link LocalDate#now() current} assignments of specified employee.
     *
     * @param employee the employee whose current assignments are found.
     * @return a list of current assignments of {@code employee}.
     * @implSpec The default implementation invokes
     * {@link #getAssignments(Employee, LocalDate) getAssignment(employee, date)} method with given {@code employee} and
     * {@link LocalDate#now() now}.
     */
    default @NotNull List<@NotNull DeptEmp> getCurrentAssignments(@Valid @NotNull @Nonnull Employee employee) {
        return getAssignments(employee, LocalDate.now());
    }

    /**
     * Assigns specified employee to specified department.
     *
     * @param employee   the employ to be assigned to the {@code department}.
     * @param department the department to which the {@code employee} is assigned.
     */
    void assign(@Valid @NotNull @Nonnull Employee employee, @Valid @NotNull @Nonnull Department department,
                @NotNull @Nonnull LocalDate fromDate);

    /**
     * Assigns specified employee to specified department, from {@link LocalDate#now() today}.
     *
     * @param employee   the employ to be assigned to the {@code department}.
     * @param department the department to which the {@code employee} is assigned.
     * @implSpec The default implementation invokes
     * {@link #assign(Employee, Department, LocalDate) assign(employee, department, fromDate)} method with
     * {@code employee}, {@code department}, and {@link LocalDate#now() today}.
     */
    default void assign(@Valid @NotNull @Nonnull Employee employee,
                        @Valid @NotNull @Nonnull Department department) {
        assign(employee, department, LocalDate.now());
    }

    /**
     * Unassigns specified employee to specified department.
     *
     * @param employee   the employ to be assigned to the {@code department}.
     * @param department the department to which the {@code employee} is assigned.
     * @param toDate     a value for {@link com.github.jinahya.mysql.employees.persistence.DeptEmp_#toDate} attribute.
     */
    void unassign(@Nonnull Employee employee, @Nonnull Department department, final LocalDate toDate);

    /**
     * Unassigns specified employee to specified department.
     *
     * @param employee   the employ to be assigned to the {@code department}.
     * @param department the department to which the {@code employee} is assigned.
     */
    void unassign(@Nonnull Employee employee, @Nonnull Department department);
}
