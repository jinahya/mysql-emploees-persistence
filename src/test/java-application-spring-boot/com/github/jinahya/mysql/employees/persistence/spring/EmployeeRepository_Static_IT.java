package com.github.jinahya.mysql.employees.persistence.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeRepository_Static_IT
        extends EmployeeRepository__IT {

    @DisplayName("PARAM_MIN_MONTH == Month.JANUARY.value")
    @Test
    void PARAM_MIN_MONTH_EqualsJANUARYValue_() {
        assertThat(EmployeeRepository.MIN_PARAM_MONTH).isEqualTo(Month.JANUARY.getValue());
    }

    @DisplayName("PARAM_MIN_MONTH == Month.DECEMBER.value")
    @Test
    void PARAM_MAX_MONTH_EqualsDECEMBERValue_() {
        assertThat(EmployeeRepository.MAX_PARAM_MONTH).isEqualTo(Month.DECEMBER.getValue());
    }
}
