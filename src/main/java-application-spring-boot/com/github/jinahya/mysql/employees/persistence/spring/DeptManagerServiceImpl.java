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
import com.github.jinahya.mysql.employees.persistence.DeptManager;
import com.github.jinahya.mysql.employees.persistence.DeptManagerService;
import com.github.jinahya.mysql.employees.persistence.Employee;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
class DeptManagerServiceImpl
        implements DeptManagerService {

    @Override
    public void appoint(final @Nonnull Employee employee, final @Nonnull Department department,
                        final @Nonnull LocalDate fromDate) {
        final var entity = new DeptManager();
        entity.getId().setEmpNo(employee.getEmpNo());                   // emp_no
        entity.getId().setDeptNo(department.getDeptNo());               // dept_no
        entity.setFromDate(fromDate);                                   // from_date
        entity.setToDate(DeptManager.COLUMN_VALUE_TO_DATE_UNSPECIFIED); // to_date
        deptManagerRepository.save(entity);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    private DeptManagerRepository deptManagerRepository;
}
