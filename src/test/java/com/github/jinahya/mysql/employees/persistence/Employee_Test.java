package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Employee_Test extends _BaseEntityTest<Employee, Integer> {

    @Nested
    class GenderTest {

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

    @Nested
    class GenderConverterTest {

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

    // -----------------------------------------------------------------------------------------------------------------
    Employee_Test() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    SingleTypeEqualsVerifierApi<Employee> equals__(final SingleTypeEqualsVerifierApi<Employee> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.SURROGATE_KEY);
    }
}
