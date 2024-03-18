package com.github.jinahya.mysql.employees.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class Employee_GenderConverter_Test
        extends Employee__Test {

    @DisplayName("convertToDatabaseColumn(attribute)")
    @Nested
    class ConvertToDatabaseColumnTest {

        @DisplayName("(null)null")
        @Test
        void _Null_Null() {
            final var instance = new Employee.GenderConverter();
            final var databaseColumn = instance.convertToDatabaseColumn(null);
            assertThat(databaseColumn).isNull();
        }

        @DisplayName("(!null)!null")
        @EnumSource(Employee.Gender.class)
        @ParameterizedTest
        void __(final Employee.Gender gender) {
            final var instance = new Employee.GenderConverter();
            final var databaseColumn = instance.convertToDatabaseColumn(gender);
            assertThat(databaseColumn).isEqualTo(gender.getColumnValue());
        }
    }

    @DisplayName("convertToEntityAttribute(dbData)")
    @Nested
    class ConvertToEntityAttribute {

        @DisplayName("(null)null")
        @Test
        void _Null_Null() {
            final var instance = new Employee.GenderConverter();
            final var entityAttribute = instance.convertToEntityAttribute(null);
            assertThat(entityAttribute).isNull();
        }

        @DisplayName("(!null)!null")
        @EnumSource(Employee.Gender.class)
        @ParameterizedTest
        void __(final Employee.Gender gender) {
            final var instance = new Employee.GenderConverter();
            final var entityAttribute = instance.convertToEntityAttribute(gender.getColumnValue());
            assertThat(entityAttribute).isEqualTo(gender);
        }
    }
}
