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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

//@org.junit.jupiter.api.Disabled
@Slf4j
class Salary_SelectAll_IT
        extends Salary__IT {

    private static List<Salary> selectAllUsingNamedQuery(final @Nonnull EntityManager entityManager,
                                                         final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createNamedQuery("Salary.selectAll", Salary.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectAllUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                            final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM Salary AS e
                                ORDER BY e.empNo, e.fromDate""",
                        Salary.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectAllUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                          final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Salary.class);
        // @formatter:off
        final var root = query.from(Salary.class);                               // FROM Salary AS e
        query.select(root);                                                      // SELECT e
        query.orderBy(                                                           // ORDER BY e.empNo ASC, e.fromDate ASC
                builder.asc(root.get(Salary_.empNo)),
                builder.asc(root.get(Salary_.fromDate))
        );
        // @formatter:on
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Salary> selectAll(final @Nonnull EntityManager entityManager, final @Nullable Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllUsingNamedQuery(entityManager, maxResults);
            case 1 -> selectAllUsingQueryLanguage(entityManager, maxResults);
            default -> selectAllUsingCriteriaApi(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void verify(final int maxResults, final List<Salary> result) {
//        assertThat(result)
//                .isNotNull()
//                .hasSizeLessThanOrEqualTo(maxResults)
//                .isSortedAccordingTo(Comparator.comparing(Salary::getIBaseEntity::getId));
    }

    @Test
    void selectAllUsingNamedQuery__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var result = applyEntityManager(em -> selectAllUsingNamedQuery(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, result);
    }

    @Test
    void selectAllUsingQueryLanguage__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var result = applyEntityManager(em -> {
            return selectAllUsingQueryLanguage(em, maxResults);
        });
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, result);
    }

    @Test
    void selectAllUsingCriteriaApi__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var result = applyEntityManager(em -> selectAllUsingCriteriaApi(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, result);
    }
}
