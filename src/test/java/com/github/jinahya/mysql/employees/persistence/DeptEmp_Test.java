package com.github.jinahya.mysql.employees.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@Slf4j
class DeptEmp_Test
        extends _BaseEntity_Test<DeptEmp, DeptEmpId> {

    DeptEmp_Test() {
        super(DeptEmp.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("setEmployee(employee)")
    @Nested
    class SetEmployeeTest {

        @DisplayName("null -> setEmpNo(null)")
        @Test
        void __Null() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var employee = (Employee) null;
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(null);
        }

        @DisplayName("non-null-with-null-empNo -> setEmpNo(null)")
        @Test
        void __NonNullWithNullId() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = newEntitySpy();
            final var employee = spy(Employee.class);
            assert employee.getEmpNo() == null;
            // ---------------------------------------------------------------------------------------------------- when
            instance.setEmployee(employee);
            // ---------------------------------------------------------------------------------------------------- then
            verify(instance, times(1)).setEmpNo(null);
        }
    }
}
