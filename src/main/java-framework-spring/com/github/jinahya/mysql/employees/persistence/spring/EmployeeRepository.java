package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

@Repository
public interface EmployeeRepository extends _BaseEntityRepository<Employee, Integer> {

    // ------------------------------------------------------------------------------------------------------- birthDate
    Page<Employee> findAllByBirthDate(LocalDate birthDate, Pageable pageable);

    Page<Employee> findAllByBirthDateBetween(LocalDate minBirthDateInclusive, LocalDate maxBirthDateInclusive,
                                             Pageable pageable);

    default Page<Employee> findAllBornOn(final YearMonth yearMonth, final Pageable pageable) {
        final var minBirthDateInclusive = yearMonth.atDay(1);
        final var maxBirthDateInclusive = yearMonth.atEndOfMonth();
        return findAllByBirthDateBetween(
                minBirthDateInclusive,
                maxBirthDateInclusive,
                pageable
        );
    }

    default Page<Employee> findAllBornIn(final Year year, final Pageable pageable) {
        final var minBirthDateInclusive = year.atDay(1);
        final var maxBirthDateInclusive = minBirthDateInclusive.with(TemporalAdjusters.lastDayOfYear());
        return findAllByBirthDateBetween(
                minBirthDateInclusive,
                maxBirthDateInclusive,
                pageable
        );
    }

    @Query("SELECT e FROM Employee AS e WHERE EXTRACT(MONTH FROM e.birthDate) = :month")
    Page<@NotNull @Valid Employee> findAllBornIn(@Param("month") @Max(12) @Min(1) int month, Pageable pageable);

    default Page<@Valid @NotNull Employee> findAllBornIn(final Month month, final Pageable pageable) {
        return findAllBornIn(month.getValue(), pageable);
    }

//    default Page<Employee> findAllBornOn(final DayOfWeek dayOfWeek, final Pageable pageable) {
//        return null;
//    }

    // ------------------------------------------------------------------------------------------------------- firstName

    // -------------------------------------------------------------------------------------------------------- lastName

    // ---------------------------------------------------------------------------------------------- firstName/lastName

    // ---------------------------------------------------------------------------------------------------------- gender

    // -------------------------------------------------------------------------------------------------------- hireDate
}
