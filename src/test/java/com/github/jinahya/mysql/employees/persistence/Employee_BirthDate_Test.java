package com.github.jinahya.mysql.employees.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

class Employee_BirthDate_Test
        extends Employee__Test {

    @DisplayName("getBirthYear()Year")
    @Nested
    class GetBirthYearTest {

        @Test
        void _Null_Null() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newEntityInstance());
            given(instance.getBirthDate()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            final var birthYear = instance.getBirthYear();
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(birthYear).isNull();
        }

        @Test
        void __() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newEntityInstance());
            final var birthDate = LocalDate.now();
            given(instance.getBirthDate()).willReturn(birthDate);
            // ---------------------------------------------------------------------------------------------------- when
            final var birthYear = instance.getBirthYear();
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(birthYear).isEqualTo(Year.from(birthDate));
        }
    }

    @DisplayName("getBirthMonth()Month")
    @Nested
    class GetBirthMonthTest {

        @Test
        void _Null_Null() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newEntityInstance());
            given(instance.getBirthDate()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            final var birthMonth = instance.getBirthMonth();
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(birthMonth).isNull();
        }

        @Test
        void __() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newEntityInstance());
            final var birthDate = LocalDate.now();
            given(instance.getBirthDate()).willReturn(birthDate);
            // ---------------------------------------------------------------------------------------------------- when
            final var birthMonth = instance.getBirthMonth();
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(birthMonth).isEqualTo(Month.from(birthDate));
        }
    }
}
