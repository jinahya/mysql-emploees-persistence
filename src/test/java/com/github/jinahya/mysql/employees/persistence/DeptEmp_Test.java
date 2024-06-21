package com.github.jinahya.mysql.employees.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
class DeptEmp_Test
        extends _BaseEntity_Test<DeptEmp, DeptEmpId> {

    DeptEmp_Test() {
        super(DeptEmp.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
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
