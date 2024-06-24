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

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
class DeptEmp_Test
        extends _BaseEntity_Test<DeptEmp, DeptEmpId> {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS
    DeptEmp_Test() {
        super(DeptEmp.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("compareTo")
    @Nested
    class CompareToTest {

        @Test
        void __() {
            final var i1 = newEntityInstance();
            i1.setEmpNo(0);
            i1.setDeptNo("0");
            i1.setFromDate(LocalDate.now());
            final var i2 = newEntityInstance();
            i2.setEmpNo(i1.getEmpNo());
            i2.setDeptNo(i1.getDeptNo());
            i2.setFromDate(LocalDate.now());
            final var result = i1.compareTo(i2);
            assertThat(result).isNotPositive();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("_ILocalDateTerm")
    @Nested
    class _ILocalDateTermTest {

        @DisplayName("getTermStart")
        @Nested
        class GetTermStartClass {

            @Test
            void __() {
                // ----------------------------------------------------------------------------------------------- given
                final var instance = newEntitySpy();
                // ------------------------------------------------------------------------------------------------ when
                final var termStart = instance.getTermStart();
                // ------------------------------------------------------------------------------------------------ then
                verify(instance, times(1)).getFromDate();
            }
        }

        @DisplayName("setTermStart")
        @Nested
        class SetTermStartTest {

            @Test
            void _Null_Null() {
                // ----------------------------------------------------------------------------------------------- given
                final var instance = newEntitySpy();
                // ------------------------------------------------------------------------------------------------ when
                instance.setTermStart(null);
                // ------------------------------------------------------------------------------------------------ then
                verify(instance, times(1)).setFromDate(null);
            }

            @Test
            void _Nonnull_Nonnull() {
                // ----------------------------------------------------------------------------------------------- given
                final var instance = newEntitySpy();
                final var termStart = LocalDate.now();
                // ------------------------------------------------------------------------------------------------ when
                instance.setTermStart(termStart);
                // ------------------------------------------------------------------------------------------------ then
                verify(instance, times(1)).setFromDate(termStart);
            }
        }

        @DisplayName("getTermEnd")
        @Nested
        class GetTermEndClass {

            @Test
            void __() {
                // ----------------------------------------------------------------------------------------------- given
                final var instance = newEntitySpy();
                // ------------------------------------------------------------------------------------------------ when
                final var termEnd = instance.getTermEnd();
                // ------------------------------------------------------------------------------------------------ then
                verify(instance, times(1)).getToDate();
            }
        }

        @DisplayName("setTermEnd")
        @Nested
        class SetTermEndTest {

            @Test
            void _Null_Null() {
                // ----------------------------------------------------------------------------------------------- given
                final var instance = newEntitySpy();
                // ------------------------------------------------------------------------------------------------ when
                instance.setTermEnd(null);
                // ------------------------------------------------------------------------------------------------ then
                verify(instance, times(1)).setToDate(null);
            }

            @Test
            void _Nonnull_Nonnull() {
                // ----------------------------------------------------------------------------------------------- given
                final var instance = newEntitySpy();
                final var termEnd = LocalDate.now();
                // ------------------------------------------------------------------------------------------------ when
                instance.setTermEnd(termEnd);
                // ------------------------------------------------------------------------------------------------ then
                verify(instance, times(1)).setToDate(termEnd);
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("setEmployee(employee)")
    @Nested
    class SetEmployeeTest {

        @DisplayName("(null) -> setEmpNo(null)")
        @Test
        void _SetEmpNoNull_NullEmployee() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var employee = (Employee) null;
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(null);
        }

        @DisplayName("nonnull-with-null-empNo -> setEmpNo(null)")
        @Test
        void _SetEmpNoNull_NonnullWithNullEmpNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var employee = Mockito.mock(Employee.class);
            given(employee.getEmpNo()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(null);
        }

        @DisplayName("nonnull-with-null-empNo -> setEmpNo(empNo)")
        @Test
        void _SetEmpNoEmpNo_NonnullWithNonnullEmpNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var employee = Mockito.mock(Employee.class);
            final var empNo = 0;
            given(employee.getEmpNo()).willReturn(empNo);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(empNo);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("setDepartment(department)")
    @Nested
    class SetDepartmentTest {

        @DisplayName("(null) -> setDeptNo(null)")
        @Test
        void _SetDeptNoNull_NullDepartment() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var department = (Department) null;
            // ---------------------------------------------------------------------------------------------------- when
            instance.setDepartment(department);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setDeptNo(null);
        }

        @DisplayName("nonnull-with-null-deptNo -> setDeptNo(null)")
        @Test
        void _SetDeptNoNull_NonnullWithNullDeptNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var department = Mockito.mock(Department.class);
            given(department.getDeptNo()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setDepartment(department);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setDeptNo(null);
        }

        @DisplayName("nonnull-with-null-deptNo -> setDeptNo(deptNo)")
        @Test
        void _SetDeptNoDeptNo_NonnullWithNonnullDeptNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var department = Mockito.mock(Department.class);
            final var deptNo = "0";
            given(department.getDeptNo()).willReturn(deptNo);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setDepartment(department);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setDeptNo(deptNo);
        }
    }
}
