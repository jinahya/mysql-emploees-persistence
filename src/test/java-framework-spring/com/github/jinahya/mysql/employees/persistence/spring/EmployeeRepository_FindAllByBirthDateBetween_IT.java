package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Employee_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class EmployeeRepository_FindAllByBirthDateBetween_IT
        extends EmployeeRepository__IT {

    static Optional<Tuple> selectMinMaxBirthDateTuple(final EntityManager entityManager) {
        try {
            return Optional.of(
                    entityManager.createNamedQuery("Employee.selectMinMaxBirthDate", Tuple.class)
                            .getSingleResult() // NoResultException
            );
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<Arguments> getMinMaxBirthDateInclusiveArgumentsList() {
        return List.of(
                selectMinMaxBirthDateTuple(entityManager())
                        .map(Tuple::toArray)
                        .map(a -> {
                            a[0] = ((LocalDate) a[0]).plusDays(ThreadLocalRandom.current().nextLong(-7300, 7300));
                            a[1] = ((LocalDate) a[1]).plusDays(ThreadLocalRandom.current().nextLong(-7300, 7300));
                            return a;
                        })
                        .map(Arguments::of)
                        .orElse(Arguments.of(LocalDate.now(), LocalDate.now()))
        );
    }

    @MethodSource({"getMinMaxBirthDateInclusiveArgumentsList"})
    @ParameterizedTest
    void __(final LocalDate minBirthDateInclusive, final LocalDate maxBirthDateInclusive) {
        final int page = 0;
        final int size = 8;
        final var sort = Sort.by(Sort.Order.asc(Employee_.birthDate.getName()));
        final var pageable = PageRequest.of(page, size, sort);
        // -------------------------------------------------------------------------------------------------------- when
        final var all = repositoryInstance().findAllByBirthDateBetween(
                minBirthDateInclusive,
                maxBirthDateInclusive,
                pageable
        );
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .hasSizeLessThanOrEqualTo(size)
                .extracting(Employee::getBirthDate)
                .isSorted()
                .allSatisfy(bd -> {
                    assertThat(bd).isBetween(minBirthDateInclusive, maxBirthDateInclusive);
                })
        ;
    }
}
