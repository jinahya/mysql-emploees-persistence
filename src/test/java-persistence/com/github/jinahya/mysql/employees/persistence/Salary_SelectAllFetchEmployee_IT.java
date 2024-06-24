package com.github.jinahya.mysql.employees.persistence;

/*-
 * #%L
 * mysql-employees-persistece
 * %%
 * Copyright (C) 2024 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Salary_SelectAllFetchEmployee_IT
        extends Salary__IT {

    private static List<Salary> selectAllFetchEmployee1(final @Nonnull EntityManager entityManager,
                                                        final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createNamedQuery("Salary.selectAllFetchEmployee", Salary.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectAllFetchEmployee2(final @Nonnull EntityManager entityManager,
                                                        final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createQuery("""
                                     SELECT e
                                     FROM Salary AS e JOIN FETCH e.employee
                                     ORDER BY e.empNo, e.fromDate""",
                             Salary.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectAllFetchEmployee3(final @Nonnull EntityManager entityManager,
                                                        final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Salary.class);
        final var root = query.from(Salary.class);                                            // FROM Salary AS e
        query.select(root);                                                                   // SELECT e
        root.fetch(Salary_.employee);                                                         //   JOIN FETCH e.employee
        query.orderBy(                                                                        // ORDER BY
                                                                                              builder.asc(root.get(
                                                                                                      Salary_.empNo)),
                                                                                              //   e.empNo
                                                                                              builder.asc(root.get(
                                                                                                      Salary_.fromDate))
                                                                                              //   e.fromDate
        );
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Salary> selectAllFetchEmployee(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllFetchEmployee1(entityManager, maxResults);
            case 1 -> selectAllFetchEmployee2(entityManager, maxResults);
            default -> selectAllFetchEmployee3(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAllFetchEmployee1__0() {
        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> {
            final var result = selectAllFetchEmployee1(em, maxResults);
            assertThat(result).extracting(Salary::getEmployee).doesNotContainNull();
            assertThat(result).extracting(super::id).isSorted();
            return result;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).extracting(Salary::getEmployee).doesNotContainNull();
        assertThat(all).isSortedAccordingTo(
                Comparator.comparing(Salary::getEmpNo)
                        .thenComparing(Salary::getFromDate)
        );
    }

    @Test
    void selectAllFetchEmployee2__0() {
        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> {
            final var result = selectAllFetchEmployee1(em, maxResults);
            assertThat(result).extracting(Salary::getEmployee).doesNotContainNull();
            assertThat(result).extracting(super::id).isSorted();
            return result;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).extracting(Salary::getEmployee).doesNotContainNull();
        assertThat(all).isSortedAccordingTo(
                Comparator.comparing(Salary::getEmpNo)
                        .thenComparing(Salary::getFromDate)
        );
    }

    @Test
    void selectAllFetchEmployee3__0() {
//        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        final var maxResults = 32;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> {
            final var result = selectAllFetchEmployee1(em, maxResults);
            assertThat(result).extracting(Salary::getEmployee).doesNotContainNull();
            assertThat(result).extracting(super::id).isSorted();
            assertThat(result).extracting(super::id).isSorted();
            result.forEach(s -> {
                final var employee = s.getEmployee();
            });
            return result;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).extracting(Salary::getEmployee).doesNotContainNull();
        assertThat(all).isSortedAccordingTo(
                Comparator.comparing(Salary::getEmpNo)
                        .thenComparing(Salary::getFromDate)
        );
        for (var salary : all) {
            log.debug("employee: {}", salary.getEmployee());
        }
    }
}
