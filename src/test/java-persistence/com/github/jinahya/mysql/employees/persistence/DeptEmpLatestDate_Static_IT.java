package com.github.jinahya.mysql.employees.persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

abstract class DeptEmpLatestDate_Static_IT
        extends DeptEmpLatestDate__IT {

    @Test
    void ATTRIBUTE_DEPT_NO__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_DEPT_NO).isEqualTo(DeptEmp_.deptNo.getName());
    }
}
