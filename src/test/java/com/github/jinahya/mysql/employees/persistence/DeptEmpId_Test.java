package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
    @DisplayName("setEmployee(employee)")
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
    @DisplayName("employee(employee)")
    @Nested
    class EmployeeTest {

        /**
         * Returns a stream of {@link Employee}s for testing.
         *
         * @return a stream of {@link Employee}s.
         */
        private static Stream<Arguments> getEmployeeStream() {
            return Stream.of(
                    Arguments.of(Named.named("null", null)),
                    Arguments.of(Named.named("non-null with null empNo", mock(Employee.class))),
                    Arguments.of(Named.named("non-null with non-null empNo", mock(Employee.class, i -> {
                        if (i.getMethod().getName().equals("getEmpNo")) {
                            return 0;
                        }
                        return null;
                    })))
            );
        }

        /**
         * Verifies that {@link DeptEmpId#employee(Employee)} method invokes {@link DeptEmpId#setEmployee(Employee)}
         * method with given arguments, and returns self.
         *
         * @param employee given argument.
         */
        @DisplayName("(employee) -> setEmployee(employee) -> self")
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
     * Returns a stream of {@link Department}s for testing.
     *
     * @return a stream of {@link Department}s.
     */
    private static Stream<Arguments> getDepartmentStream() {
        return Stream.of(
                Arguments.of(Named.named("null", null)),
                Arguments.of(Named.named("non-null with null deptNo", mock(Department.class))),
                Arguments.of(Named.named("non-null with non-null deptNo", mock(Department.class, i -> {
                    if (i.getMethod().getName().equals("getDeptNo")) {
                        return "0";
                    }
                    return null;
                })))
        );
    }

    /**
     * {@link DeptEmpId#setDepartment(Department) setDepartment(department)} 메서드를 호출했을 때,
     * {@link DeptEmpId#setDeptNo(String) setDeptNo(department?.deptNo} 를 한 번 호출한다.
     *
     * @param department a department to test with.
     */
    @DisplayName("setDepartment(department) -> setDeptNo(department?.deptNo)")
    @MethodSource({"getDepartmentStream"})
    @ParameterizedTest
    void setDepartment_setDeptNoWithDepartmentDeptNo_(final Department department) {
        // --------------------------------------------------------------------------------------------------- given
        final var instance = newIdSpy();
        // ---------------------------------------------------------------------------------------------------- when
        instance.setDepartment(department);
        // ---------------------------------------------------------------------------------------------------- then
        // TODO: verify, setDeptNo(department?.deptNo) invoked, once
    }

    /**
     * {@link DeptEmpId#department(Department) department(department)} 메서드를 호출했을 때,
     * {@link DeptEmpId#setDepartment(Department) setDepartment(department)} 를 한 번 호출한 후 가지 자신을 반환한다.
     *
     * @param department a department to test with.
     */
    @DisplayName("department(department) -> setDepartment(department) -> self")
    @MethodSource({"getDepartmentStream"})
    @ParameterizedTest
    void department_setDepartmentWithDepartment_(final Department department) {
        // --------------------------------------------------------------------------------------------------- given
        final var instance = newIdSpy();
        // ---------------------------------------------------------------------------------------------------- when
        final var result = instance.department(department);
        // ---------------------------------------------------------------------------------------------------- then
        // TODO: verify, setDepartment(department) invoked, once.
        // TODO: assert, result is same as instance.
    }
}
