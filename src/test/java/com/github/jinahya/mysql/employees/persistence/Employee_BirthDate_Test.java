package com.github.jinahya.mysql.employees.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.Temporal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class Employee_BirthDate_Test
        extends Employee__Test {

    /**
     * A class for testing {@link Employee#getBirthYear()} method.
     */
    @DisplayName("getBirthYear()Year")
    @Nested
    class GetBirthYearTest {

        @DisplayName("getBirthDate()null")
        @Test
        void _Null_Null() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newEntityInstance());
            given(instance.getBirthDate()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            final var birthYear = instance.getBirthYear();
            // ---------------------------------------------------------------------------------------------------- then
            // https://github.com/assertj/assertj/issues/3491
            assertThat((Temporal) birthYear).isNull();
        }

        @DisplayName("getBirthDate()!null")
        @Test
        void __() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newEntityInstance());
            final var birthDate = LocalDate.now();
            given(instance.getBirthDate()).willReturn(birthDate);
            // ---------------------------------------------------------------------------------------------------- when
            final var birthYear = instance.getBirthYear();
            // ---------------------------------------------------------------------------------------------------- then
            // https://github.com/assertj/assertj/issues/3491
            assertThat((Temporal) birthYear).isEqualTo(Year.from(birthDate));
        }
    }

    @DisplayName("getBirthYearMonth()YearMonth")
    @Nested
    class GetBirthYearMonthTest {

        @Test
        void _Null_Null() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newEntityInstance());
            given(instance.getBirthDate()).willReturn(null);
            // ---------------------------------------------------------------------------------------------------- when
            final var birthYearMonth = instance.getBirthYearMonth();
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(birthYearMonth).isNull();
            verify(instance, times(1)).applyBirthDate(notNull());
            verify(instance, atLeast(1)).getBirthDate(); // when woven
        }

        @Test
        void __() {
            // --------------------------------------------------------------------------------------------------- given
            final var instance = spy(newEntityInstance());
            final var birthDate = LocalDate.now();
            given(instance.getBirthDate()).willReturn(birthDate);
            // ---------------------------------------------------------------------------------------------------- when
            final var birthYearMonth = instance.getBirthYearMonth();
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(birthYearMonth).isEqualTo(YearMonth.from(birthDate));
            verify(instance, times(1)).applyBirthDate(notNull());
            verify(instance, atLeast(1)).getBirthDate(); // when woven
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
            verify(instance, times(1)).applyBirthDate(notNull());
            verify(instance, atLeast(1)).getBirthDate(); // when woven
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
            verify(instance, times(1)).applyBirthDate(notNull());
            verify(instance, atLeast(1)).getBirthDate(); // when woven
        }
    }

    @DisplayName("getBirthMonthDay()MonthDay")
    @Nested
    class GetBirthMonthDayTest {

        @Test
        void _Null_Null() {
            // TODO: implement!
        }

        @Test
        void __() {
            // TODO: implement! 
        }
    }

    @DisplayName("getBirthDayOfMonth()DayOfMonth")
    @Nested
    class GetBirthDayOfMonthTest {

        @Test
        void _Null_Null() {
            // TODO: implement!
        }

        @Test
        void __() {
            // TODO: implement! 
        }
    }
}
