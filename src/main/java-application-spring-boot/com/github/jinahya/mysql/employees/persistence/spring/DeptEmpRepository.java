package com.github.jinahya.mysql.employees.persistence.spring;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * A repository interface for {@link DeptEmp} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Repository
public interface DeptEmpRepository
        extends _BaseEntityRepository<DeptEmp, DeptEmpId> {

    // -------------------------------------------------------------------------------------------------- empNo/employee
    Page<DeptEmp> findAllByEmployee(Employee employee, Pageable pageable);

    // ----------------------------------------------------------------------------------------------- deptNo/department
    Page<DeptEmp> findAllByDepartment(Department department, Pageable pageable);

    // --------------------------------------------------------------------------------------------- employee/department
    @Deprecated
    Optional<DeptEmp> findByEmployeeAndDepartment(Employee employee, Department department);

    @Deprecated
    default Optional<DeptEmp> findByEmployeeAndDepartment1(final Employee employee, final Department department) {
        return findById(
                DeptEmpId.from(employee, department)
        );
    }

    // -------------------------------------------------------------------------------------------------------- fromDate

    // ---------------------------------------------------------------------------------------------------------- toDate

    // -----------------------------------------------------------------------------------------------------------------
    Page<DeptEmp> findAllByDepartmentAndFromDateGreaterThanEqualAndToDateLessThanEqual(Department department,
                                                                                       LocalDate fromDateMinInclusive,
                                                                                       LocalDate toDateMaxInclusive,
                                                                                       Pageable pageable);
}
