package com.github.jinahya.mysql.employees.persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Salary_Static_IT
        extends Salary__IT {

    @Test
    void ATTRIBUTE_NAME_FROM_DATE__() {
        assertThat(Salary.ATTRIBUTE_NAME_FROM_DATE)
                .isEqualTo(Salary_.fromDate.getName());
    }
}
