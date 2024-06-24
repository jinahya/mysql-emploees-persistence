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
class Employee_SelectAll_IT
        extends _BaseEntityIT<Employee, Integer> {

    private static List<Employee> selectAllUsingNamedQuery(final EntityManager entityManager,
                                                           final @Nullable Integer maxResult) {
        return entityManager
                .createNamedQuery("Employee.selectAll", Employee.class)
                .setMaxResults(Optional.ofNullable(maxResult).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Employee> selectAllUsingQueryLanguage(final EntityManager entityManager,
                                                              final @Nullable Integer maxResult) {
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM Employee AS e
                                ORDER BY e.empNo ASC""",
                        Employee.class
                )
                .setMaxResults(Optional.ofNullable(maxResult).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Employee> selectAllUsingCriteriaApi(final EntityManager entityManager,
                                                            final @Nullable Integer maxResult) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Employee.class);
        final var employee = query.from(Employee.class);                                           // FROM Employee AS e
        query.select(employee);                                                                    // SELECT e
        query.orderBy(builder.asc(employee.get(Employee_.empNo)));                                 // ORDER BY e.empNo
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResult).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Employee> selectAll(final EntityManager entityManager, final Integer maxResult) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        if (maxResult != null && maxResult < 0) {
            throw new IllegalArgumentException("negative maxResult: " + maxResult);
        }
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllUsingNamedQuery(entityManager, maxResult);
            case 1 -> selectAllUsingQueryLanguage(entityManager, maxResult);
            default -> selectAllUsingCriteriaApi(entityManager, maxResult);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    Employee_SelectAll_IT() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAllUsingNamedQuery__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingNamedQuery(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Employee::getEmpNo));
        if (maxResults != null) {
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAllUsingQueryLanguage__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingQueryLanguage(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Employee::getEmpNo));
        if (maxResults != null) {
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAllUsingCriteriaApi__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingCriteriaApi(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Employee::getEmpNo));
        if (maxResults != null) {
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
