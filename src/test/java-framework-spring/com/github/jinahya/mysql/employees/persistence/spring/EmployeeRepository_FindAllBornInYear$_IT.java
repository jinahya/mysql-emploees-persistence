package com.github.jinahya.mysql.employees.persistence.spring;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
abstract class EmployeeRepository_FindAllBornInYear$_IT extends EmployeeRepository__IT {

    static List<Integer> selectDistinctBirthYearList(final EntityManager entityManager,
                                                     final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT DISTINCT EXTRACT(YEAR FROM e.birthDate) AS birthYear
                                FROM Employee AS e
                                ORDER BY birthYear ASC""",
                        Integer.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }
}