package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class DeptEmpId_Test
        extends _BaseId_Test<DeptEmpId> {

    DeptEmpId_Test() {
        super(DeptEmpId.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    SingleTypeEqualsVerifierApi<DeptEmpId> equals__(final SingleTypeEqualsVerifierApi<DeptEmpId> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.NONFINAL_FIELDS);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A nested test class for testing {@link DeptEmpId#setEmployee(Employee)} method.
     */
    @DisplayName("setEmployee(v)")
    @Nested
    class SetEmployeeTest {

        @DisplayName("(null)")
        @Test
        void __Null() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newIdSpy();
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(null);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(null);
        }

        @DisplayName("(non-null-with-null-emp-no)")
        @Test
        void __NonNullWithNullEmpNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newIdSpy();
            final var employee = mock(Employee.class);
            given(employee.getEmpNo()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(null);
        }

        @DisplayName("(non-null-with-null-emp-no)")
        @Test
        void __NonNullWithNonNullEmpNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newIdSpy();
            final var employee = mock(Employee.class);
            final var empNo = 0;
            given(employee.getEmpNo()).willReturn(empNo);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(empNo);
        }
    }

    /**
     * A nested class for testing {@link DeptEmpId#employee(Employee)} method.
     */
    @DisplayName("employee(v)")
    @Nested
    class EmployeeTest {

        /**
         * Returns a stream of {@link Employee}s for testing.
         *
         * @return a stream of {@link Employee}s.
         */
        private static Stream<Employee> getEmployeeStream() {
            return Stream.of(
                    null,
                    mock(Employee.class),
                    mock(Employee.class, i -> {
                        if (i.getMethod().getName().equals("getEmpNo")) {
                            return 0;
                        }
                        return null;
                    })
            );
        }

        /**
         * Verifies that {@link DeptEmpId#employee(Employee)} method invokes {@link DeptEmpId#setEmployee(Employee)}
         * method with given arguments, and returns self.
         *
         * @param employee given argument.
         */
        @DisplayName("(v) -> setEmployee(v) -> v")
        @MethodSource({"getEmployeeStream"})
        @ParameterizedTest
        void __(final Employee employee) {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newIdSpy();
            // ---------------------------------------------------------------------------------------------------- when
            final var result = instance.employee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmployee(employee);
            assertThat(result).isSameAs(instance);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A nested test class for testing {@link DeptEmpId#setDepartment(Department)} method.
     */
    @DisplayName("setDepartment(Department)")
    @Nested
    class SetDepartmentTest {

        @DisplayName("(null)")
        @Test
        void __Null() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newIdSpy();
            // ---------------------------------------------------------------------------------------------------- when
            instance.setDepartment(null);
            // ---------------------------------------------------------------------------------------------------- then
            // TODO: verify, setDeptNo(null) invoked, once
        }

        @DisplayName("(non-null-with-null-emp-no)")
        @Test
        void __NonNullWithNullDeptNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newIdSpy();
            final var department = mock(Department.class);
            given(department.getDeptNo()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setDepartment(department);
            // ---------------------------------------------------------------------------------------------------- then
            // TODO: verify , setDeptNo(null) invoked, once.
        }

        @DisplayName("(non-null-with-null-emp-no)")
        @Test
        void __NonNullWithNonNullDeptNo() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newIdSpy();
            final var department = mock(Department.class);
            final var deptNo = "0";
            given(department.getDeptNo()).willReturn(deptNo);
            // ---------------------------------------------------------------------------------------------------- when
            instance.setDepartment(department);
            // ---------------------------------------------------------------------------------------------------- then
            // TODO: verify, setDeptNo(deptNo) invoked, once.
        }
    }

    /**
     * A nested class for testing {@link DeptEmpId#department(Department)} method.
     */
    @DisplayName("department(v)")
    @Nested
    class DepartmentTest {

        /**
         * Returns a stream of {@link Department}s for testing.
         *
         * @return a stream of {@link Department}s.
         */
        private static Stream<Department> getDepartmentStream() {
            return Stream.of(
                    null,                         // null
                    mock(Department.class),       // non-null with null deptNo
                    mock(Department.class, i -> { // non-null with no null deptNo
                        if (i.getMethod().getName().equals("getDeptNo")) {
                            return "0";
                        }
                        return null;
                    })
            );
        }

        /**
         * Verifies that {@link DeptEmpId#department(Department)} method invokes
         * {@link DeptEmpId#setDepartment(Department)} method with given arguments, and returns self.
         *
         * @param department given argument.
         */
        @DisplayName("(v) -> setDepartment(v) -> v")
        @MethodSource({"getDepartmentStream"})
        @ParameterizedTest
        void __(final Department department) {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newIdSpy();
            // ---------------------------------------------------------------------------------------------------- when
            final var result = instance.department(department);
            // ---------------------------------------------------------------------------------------------------- then
            // TODO: verify, setDepartment(department) invoked, once.
            // TODO: assert, result is same as instance.
        }
    }
}
