package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Employee_;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import static com.github.jinahya.mysql.employees.persistence.spring._BaseEntityRepositoryConstants.*;

/**
 * A repository interface for {@link Employee} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a
 * href="https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/criteria/criteriabuilder#extract(jakarta.persistence.criteria.TemporalField,jakarta.persistence.criteria.Expression)">CriteriaBuilder#extract</a>
 * (3.2)
 */
@Repository
public interface EmployeeRepository
        extends _BaseEntityRepository<Employee, Integer> {

    // ----------------------------------------------------------------------------------------------------------- empNo

    /**
     * Finds the one whose {@link Employee_#empNo empNo} attribute matches specified value.
     *
     * @param empNo the value {@link Employee_#empNo empNo} attribute to match.
     * @return an optional of found entity; {@link Optional#empty() empty} if not found.
     * @deprecated Use {@link org.springframework.data.repository.CrudRepository#findById(Object)}.
     */
    @Deprecated
    Optional<Employee> findByEmpNo(Integer empNo); // TODO: remove!

    // ------------------------------------------------------------------------------------------------------- birthDate
    Page<Employee> findAllByBirthDate(LocalDate birthDate, Pageable pageable);

    Page<Employee> findAllByBirthDateBetween(LocalDate minBirthDateInclusive, LocalDate maxBirthDateInclusive,
                                             Pageable pageable);

    // -----------------------------------------------------------------------------------------------------------------
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
    Page<Employee> findAllBornInYear2(@Param(PARAM_NAME_YEAR) int year, Pageable pageable);

    default Page<Employee> findAllBornInYear3(@Param(PARAM_NAME_YEAR) int year, Pageable pageable) {
        // TODO: Implement using Criteria API
        // TODO: See Jakarta Persistence 3.2 / CriteriaBuilder#extract
        // https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2-m2#a5304
        return null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Query("""
            SELECT e
            FROM Employee AS e
            WHERE EXTRACT(MONTH FROM e.birthDate) = :month""")
    Page<@NotNull @Valid Employee> findAllBornInMonth1(
            @Max(PARAM_VALUE_MONTH_MIN)
            @Min(PARAM_VALUE_MONTH_MAX)
            @Param(PARAM_NAME_MONTH) Integer month,
            Pageable pageable);

    default Page<@NotNull @Valid Employee> findAllBornInMonth1(final Month month, Pageable pageable) {
        return findAllBornInMonth1(month.getValue(), pageable);
    }

    default Page<Employee> findAllBornInMonth3(@Param(PARAM_NAME_MONTH) int month, Pageable pageable) {
        // TODO: Implement using Criteria API
        // TODO: See Jakarta Persistence 3.2 / CriteriaBuilder#extract
        // https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2-m2#a5304
        return null;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Finds all employees who were born in specified year/month.
     *
     * @param yearMonth the year/month.
     * @param pageable  a pagination info.
     * @return a page of found entities.
     */
    default Page<@Valid @NotNull Employee> findAllBornInYearMonth1(final YearMonth yearMonth, final Pageable pageable) {
        final var minBirthDateInclusive = yearMonth.atDay(1);
        final var maxBirthDateInclusive = yearMonth.atEndOfMonth();
        return findAllByBirthDateBetween(
                minBirthDateInclusive,
                maxBirthDateInclusive,
                pageable
        );
    }

    @Query("""
            SELECT e
            FROM Employee AS e
            WHERE EXTRACT(YEAR FROM e.birthDate) = :year
              AND EXTRACT(MONTH FROM e.birthDate) = :month""")
    Page<@Valid @NotNull Employee> findAllBornInYearMonth2(@Param(PARAM_NAME_MONTH) final int year,
                                                           @Max(PARAM_VALUE_MONTH_MIN)
                                                           @Min(PARAM_VALUE_MONTH_MAX)
                                                           @Param(PARAM_NAME_MONTH) int month,
                                                           final Pageable pageable);

    default Page<@Valid @NotNull Employee> findAllBornInYearMonth2(final int year, final Month month,
                                                                   final Pageable pageable) {
        return findAllBornInYearMonth2(year, month.getValue(), pageable);
    }

    default Page<@Valid @NotNull Employee> findAllBornInYearMonth3(final YearMonth yearMonth, final Pageable pageable) {
        // TODO: Implement using Criteria API
        // TODO: See Jakarta Persistence 3.2 / CriteriaBuilder#extract
        // https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2-m2#a5304
        return null;
    }

    // ------------------------------------------------------------------------------------------------------- firstName

    /**
     * Finds all entities whose {@link Employee_#firstName firstName} attribute matches specified value.
     *
     * @param firstName the {@link Employee_#firstName firstName} attribute value to match.
     * @param pageable  pagination info.
     * @return a page of found entities.
     */
    Page<Employee> findAllByFirstName(String firstName, Pageable pageable);

    // -------------------------------------------------------------------------------------------------------- lastName
    // TODO: add javadoc!
    Page<Employee> findAllByLastName(String lastName, Pageable pageable);

    // ---------------------------------------------------------------------------------------------- firstName/lastName

    /**
     * Finds all entities whose {@link Employee_#firstName firstName} attribute  and {@link Employee_#lastName lastName}
     * attribute match specified values.
     *
     * @param firstName the {@link Employee_#firstName firstName} attribute value to match.
     * @param lastName  the {@link Employee_#lastName lastName} attribute value to match.
     * @param pageable  pagination info.
     * @return a page of found entities.
     */
    Page<Employee> findAllByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

    // ---------------------------------------------------------------------------------------------------------- gender
    // TODO: add javadoc!
    Page<Employee> findAllByGender(Employee.Gender gender, Pageable pageable);

    // -------------------------------------------------------------------------------------------------------- hireDate
    Page<Employee> findAllByHireDate(LocalDate hireDate, Pageable pageable);

    Page<Employee> findAllByHireDateBetween(LocalDate minHireDateInclusive, LocalDate maxHireDateInclusive,
                                            Pageable pageable);
}
