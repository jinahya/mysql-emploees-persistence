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

/**
 * A repository interface for {@link Employee} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a
 * href="https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/criteria/criteriabuilder#extract(jakarta.persistence.criteria.TemporalField,jakarta.persistence.criteria.Expression)">CriteriaBuilder#extract</a>
 * (3.2)
 */
@Repository
public interface EmployeeRepository extends _BaseEntityRepository<Employee, Integer> {

    String PARAM_YEAR = "year";

    String PARAM_MONTH = "month";

    int MIN_PARAM_MONTH = 1;

    int MAX_PARAM_MONTH = 12;

    // ------------------------------------------------------------------------------------------------------- birthDate
    Page<Employee> findAllByBirthDate(LocalDate birthDate, Pageable pageable);

    Page<Employee> findAllByBirthDateBetween(LocalDate minBirthDateInclusive, LocalDate maxBirthDateInclusive,
                                             Pageable pageable);

    default Page<Employee> findAllBornInYear1(final Year year, final Pageable pageable) {
        final var minBirthDateInclusive = year.atDay(1);
        final var maxBirthDateInclusive = minBirthDateInclusive.with(TemporalAdjusters.lastDayOfYear());
        return findAllByBirthDateBetween(
                minBirthDateInclusive,
                maxBirthDateInclusive,
                pageable
        );
    }

    @Query("""
            SELECT e
            FROM Employee AS e
            WHERE EXTRACT(YEAR FROM e.birthDate) = :year""")
    Page<Employee> findAllBornInYear2(@Param(PARAM_YEAR) int year, Pageable pageable);

    default Page<Employee> findAllBornInYear3(@Param(PARAM_YEAR) int year, Pageable pageable) {
        // TODO: Implement using Criteria API
        // TODO: See Jakarta Persistence 3.2 / CriteriaBuilder#extract
        return null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Query("""
            SELECT e
            FROM Employee AS e
            WHERE EXTRACT(MONTH FROM e.birthDate) = :month""")
    Page<@NotNull @Valid Employee> findAllBornInMonth(
            @Param(PARAM_MONTH) @Max(MAX_PARAM_MONTH) @Min(MIN_PARAM_MONTH) int month,
            Pageable pageable);

    default Page<@NotNull @Valid Employee> findAllBornInMonth(final Month month, Pageable pageable) {
        return findAllBornInMonth(month.getValue(), pageable);
    }

    // -----------------------------------------------------------------------------------------------------------------
    default Page<@Valid @NotNull Employee> findAllBornInYearMonth(final YearMonth yearMonth, final Pageable pageable) {
        final var minBirthDateInclusive = yearMonth.atDay(1);
        final var maxBirthDateInclusive = yearMonth.atEndOfMonth();
        return findAllByBirthDateBetween(
                minBirthDateInclusive,
                maxBirthDateInclusive,
                pageable
        );
    }

    // ------------------------------------------------------------------------------------------------------- firstName

    // -------------------------------------------------------------------------------------------------------- lastName

    // ---------------------------------------------------------------------------------------------- firstName/lastName

    // ---------------------------------------------------------------------------------------------------------- gender

    // -------------------------------------------------------------------------------------------------------- hireDate
    Page<Employee> findAllByHireDateBetween(LocalDate minHireDateInclusive, LocalDate maxHireDateInclusive,
                                            Pageable pageable);

    default Page<Employee> findAllHiredIn(final Year year, final Pageable pageable) {
        // TODO: implement!
        return null;
    }
}
