package com.github.jinahya.mysql.employees.persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeptEmp_Constants_IT
        extends DeptEmp__IT {

    @Test
    void ATTRIBUTE_NAME_EMP_NO__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_EMP_NO)
                .isEqualTo(DeptEmp_.empNo.getName());
    }

    @Test
    void ATTRIBUTE_NAME_EMPLOYEE__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_EMPLOYEE)
                .isEqualTo(DeptEmp_.employee.getName());
    }

    @Test
    void ATTRIBUTE_DEPT_NO__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_DEPT_NO)
                .isEqualTo(DeptEmp_.deptNo.getName());
    }

    @Test
    void ATTRIBUTE_NAME_DEPARTMENT__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_DEPARTMENT)
                .isEqualTo(DeptEmp_.department.getName());
    }
}
