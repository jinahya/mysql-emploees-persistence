package com.github.jinahya.mysql.employees.persistence.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class _BaseEntityRepositoryConstantsTest {

    @DisplayName("MIN_PARAM_VALUE_MONTH == JANUARY.value")
    @Test
    void MIN_PARAM_VALUE_MONTH_JANUARY_() {
        assertThat(_BaseEntityRepositoryConstants.PARAM_VALUE_MONTH_MIN)
                .isEqualTo(Month.JANUARY.getValue());
    }

    @DisplayName("MAX_PARAM_VALUE_MONTH == DECEMBER.value")
    @Test
    void MAX_PARAM_VALUE_MONTH_DECEMBER_() {
        assertThat(_BaseEntityRepositoryConstants.PARAM_VALUE_MONTH_MAX)
                .isEqualTo(Month.DECEMBER.getValue());
    }
}