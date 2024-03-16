package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Employee_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class EmployeeRepository_FindAllByBirthDate_IT extends EmployeeRepository__IT {

    static List<LocalDate> selectDistinctBirthDateList(final EntityManager entityManager,
                                                       final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT DISTINCT e.birthDate AS distinctBirthDate
                                FROM Employee AS e
                                ORDER BY distinctBirthDate ASC""",
                        LocalDate.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Tuple> selectBirthDateAndCountTupleList(final EntityManager entityManager,
                                                        final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e.birthDate, COUNT(e) AS c
                                FROM Employee AS e
                                GROUP BY e.birthDate
                                HAVING COUNT(e) > 1
                                ORDER BY c DESC""",
                        Tuple.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<LocalDate> getBirthDateList() {
        try {
            return selectDistinctBirthDateList(entityManager(), 8);
        } finally {
            entityManager().clear();
        }
    }

    @MethodSource({"getBirthDateList"})
    @ParameterizedTest
    void __(final LocalDate birthDate) {
//        final int size = ThreadLocalRandom.current().nextInt(16);
        final int size = 1;
        final var all = repositoryInstance().findAllByBirthDate(birthDate, Pageable.ofSize(size));
        assertThat(all)
                .hasSizeLessThanOrEqualTo(size)
                .extracting(Employee::getBirthDate)
                .containsOnly(birthDate);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<Arguments> getBirthDateAndCountArgumentsList() {
        try {
            return selectBirthDateAndCountTupleList(entityManager(), 8)
                    .stream()
                    .map(Tuple::toArray)
                    .map(Arguments::of)
                    .toList();
        } finally {
            entityManager().clear();
        }
    }

    @MethodSource({"getBirthDateAndCountArgumentsList"})
    @ParameterizedTest
    void __(final LocalDate birthDate, final long count) {

        final int page = 0;
        final int size = Math.toIntExact(count);
        final var sort = Sort.by(Sort.Order.asc(Employee_.birthDate.getName()));
        final var pageable = PageRequest.of(page, size, sort);
        // -------------------------------------------------------------------------------------------------------- when
        final var all = repositoryInstance().findAllByBirthDate(birthDate, pageable);
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .hasSize(size)
                .extracting(Employee::getBirthDate)
                .isSorted()
                .containsOnly(birthDate)
        ;
    }
}
