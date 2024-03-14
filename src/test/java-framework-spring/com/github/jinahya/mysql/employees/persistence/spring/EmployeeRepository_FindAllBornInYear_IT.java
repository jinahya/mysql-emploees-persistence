package com.github.jinahya.mysql.employees.persistence.spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class EmployeeRepository_FindAllBornInYear_IT extends EmployeeRepository__IT {

    static List<LocalDate> selectDistinctBirthYearMonthList(final EntityManager entityManager,
                                                            final @Nullable Integer maxResults) {
        return entityManager
                .createQuery("""
                                     SELECT DISTINCT EXTRACT(YEAR FROM e.birthDate), EXTRACT(MONTH FROM e.birthDate)
                                     FROM Employee AS e""",
                             LocalDate.class)
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
}
