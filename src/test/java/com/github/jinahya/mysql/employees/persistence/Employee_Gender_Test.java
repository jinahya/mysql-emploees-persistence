package com.github.jinahya.mysql.employees.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Employee_Gender_Test
        extends Employee__Test {

    @Test
    void allColumnValuesAreUnique__() {
        assertThat(Arrays.stream(Employee.Gender.values()).map(Employee.Gender::getColumnValue))
                .isNotEmpty()
                .allMatch(new HashSet<>()::add);
    }

    @DisplayName("valueOfColumnValue(known)")
    @EnumSource(Employee.Gender.class)
    @ParameterizedTest
    void valueOfColumnValue__(final Employee.Gender expected) {
        final var actual = Employee.Gender.valueOfColumnValue(expected.getColumnValue());
        assertThat(actual).isSameAs(expected);
    }

    @DisplayName("valueOfColumnValue(unknown)")
    @Test
    void valueOfColumnValue_IllegalArgumentException_Unknown() {
        assertThatThrownBy(() -> {
            Employee.Gender.valueOfColumnValue("X");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
