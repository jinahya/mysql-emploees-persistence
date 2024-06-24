package com.github.jinahya.mysql.employees.persistence;

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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeptManagerId_Test
        extends _BaseId_Test<DeptManagerId> {

    DeptManagerId_Test() {
        super(DeptManagerId.class);
    }

    @DisplayName("employee(employee) -> setEmpNo(employee?.empNo) -> returns self")
    @Nested
    class EmployeeTest {

        @Test
        void _Null_Null() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newIdInstance());
            // ---------------------------------------------------------------------------------------------------- when
            final var result = instance.employee(null);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(null);
            assertThat(result).isSameAs(instance);
        }

        @Test
        void _Null_NonNullWithNullEmpNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newIdInstance());
            final var employee = new Employee();
            assert employee.getEmpNo() == null;
            // ---------------------------------------------------------------------------------------------------- when
            final var result = instance.employee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(null);
            assertThat(result).isSameAs(instance);
        }

        @Test
        void __() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newIdInstance());
            final var employee = new Employee();
            employee.setEmpNo(ThreadLocalRandom.current().nextBoolean() ? null : 0);
            // ---------------------------------------------------------------------------------------------------- when
            final var result = instance.employee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(employee.getEmpNo());
            assertThat(result).isSameAs(instance);
        }
    }

    @DisplayName("department(department) -> setDeptNo(department?.deptNo) -> returns self")
    @Nested
    class DepartmentTest {
        // TODO: implement!
    }
}
