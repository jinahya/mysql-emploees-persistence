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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("selectAll")
@Slf4j
class Department_SelectAll_IT
        extends Department__IT {

    private static List<Department> selectAllUsingNamedQuery(final @Nonnull EntityManager entityManager,
                                                             final @Nullable Integer maxResults) {
        return entityManager
                .createNamedQuery("Department.selectAll", Department.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Department> selectAllUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                                final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM Department AS e
                                ORDER BY e.deptNo ASC""",
                        Department.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Department> selectAllUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                              final @Nullable Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Department.class);
        final var root = query.from(Department.class);                                          // FROM Department AS e
        query.select(root);                                                                     // SELECT e
        query.orderBy(builder.asc(root.get(Department_.deptNo)));                               // ORDER BY e.deptNo ASC
        return entityManager.createQuery(query)
                            .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                            .getResultList();
    }

    static List<Department> selectAll(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllUsingNamedQuery(entityManager, maxResults);
            case 1 -> selectAllUsingQueryLanguage(entityManager, maxResults);
            default -> selectAllUsingCriteriaApi(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void verify(final Integer maxResults, final List<Department> all) {
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Department::getDeptNo));
        if (maxResults != null) {
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
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
        verify(maxResults, all);
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
        verify(maxResults, all);
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
        verify(maxResults, all);
    }
}
