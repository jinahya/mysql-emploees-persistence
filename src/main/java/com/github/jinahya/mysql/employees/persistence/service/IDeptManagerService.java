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
import com.github.jinahya.mysql.employees.persistence.DeptManager;
import com.github.jinahya.mysql.employees.persistence.DeptManagerId;
import com.github.jinahya.mysql.employees.persistence.Employee;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public interface IDeptManagerService
        extends _IBaseEntityService<DeptManager, DeptManagerId> {

    /**
     * Appoints specified employee as the manager of specified department, as of specified date.
     *
     * @param employee   the employee to be appointed.
     * @param department the department to which the {@code employee} is appointed as the manager.
     * @param fromDate   the date of appointment.
     */
    void appoint(@Nonnull Employee employee, @Nonnull Department department, @Nonnull LocalDate fromDate);
}
