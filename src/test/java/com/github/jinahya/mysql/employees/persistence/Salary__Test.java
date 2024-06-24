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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class Salary__Test
        extends _BaseEntity_Test<Salary, SalaryId> {

    Salary__Test() {
        super(Salary.class);
    }

    // ------------------------------------------------------------------------------------------- getEmployee()Employee
    @DisplayName("getEmployee()Employee")
    @Nested
    class GetEmployeeTest {

        @DisplayName("newInstance().getEmployee()Null")
        @Test
        void _Null_NewInstance() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            // ---------------------------------------------------------------------------------------------------- when
            final var result = instance.getEmployee();
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(result).isNull();
            verify(instance, only()).getEmployee();
        }
    }

    // ------------------------------------------------------------------------------------------ setEmployee(Employee)V
    @DisplayName("setEmployee(Employee)V")
    @Nested
    class SetEmployeeTest {

        /**
         * Verifies that the {@link Salary#setEmployee(Employee) setEmployee(employee)} method, invoked with
         * {@code null}, invokes {@link Salary#setEmpNo(Integer) setEmpNo(empNo)} method with {@code null}.
         */
        @DisplayName("(null) should invoke setEmpNo(null)")
        @Test
        void _SetEmpNoWithNull_EmployeeIsNull() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(null);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmployee(null);
            verify(instance, times(1)).setEmpNo(null);
            verifyNoMoreInteractions(instance);
        }

        /**
         * Verifies that the {@link Salary#setEmployee(Employee) setEmployee(employee)} method, invoked with an employee
         * whose {@link Employee_#empNo empNo} attribute is {@code null}, invokes
         * {@link Salary#setEmpNo(Integer) setEmpNo(empNo)} method with {@code null}.
         */
        @DisplayName("(nonnull-with-null-empNo) should invoke setEmpNo(null)")
        @Test
        void _SetEmpNoWithNull_EmployeeWithNullEmpNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var employee = mock(Employee.class);
            given(employee.getEmpNo()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            // TODO: verify, setEmpNo(null) invoked, once
        }

        /**
         * Verifies that the {@link Salary#setEmployee(Employee) setEmployee(employee)} method, invoked with an
         * employee, invokes {@link Salary#setEmpNo(Integer) setEmpNo(empNo)} method with {@code employee.empNo}.
         */
        @DisplayName("(nonnull-with-nonnull-empNo) should invoke setEmpNo(employee.empNo)")
        @Test
        void _SetEmpNoWithEmployeeEmpNo_EmployeeWithNonnullEmpNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var employee = mock(Employee.class);
            final var empNo = ThreadLocalRandom.current().nextInt();
            given(employee.getEmpNo()).willReturn(empNo);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            // TODO: verify, setEmpNo(empNo) invoked, once
        }
    }

    // ---------------------------------------------------------------------------------------- employee(Employee)Salary
    @DisplayName("employee(Employee)Salary")
    @Nested
    class EmployeeTest {

        @DisplayName("(null) should invoke setEmployee(null)")
        @Test
        void _InvokeSetEmployeeWithNull_EmployeeIsNull() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            // ---------------------------------------------------------------------------------------------------- when
            final var result = instance.employee(null);
            // ---------------------------------------------------------------------------------------------------- then
            // TODO: verify, setEmployee(null) invoked, once
            // TODO: verify, result is same as instance
        }

        @DisplayName("(nonnull) should invoke setEmployee(given)")
        @Test
        void _InvokeSetEmployeeWithGiven_EmployeeIsNotNull() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var employee = mock(Employee.class);
            // ---------------------------------------------------------------------------------------------------- when
            final var result = instance.employee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            // TODO: verify, setEmployee(employee) invoked, once
            // TODO: verify, result is same as instance
        }
    }
}
