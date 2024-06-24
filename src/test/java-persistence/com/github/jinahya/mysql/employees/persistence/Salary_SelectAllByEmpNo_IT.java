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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

//@org.junit.jupiter.api.Disabled
class Salary_SelectAllByEmpNo_IT
        extends Salary__IT {

    private static final String PARAMETER_EMP_NO = "empNo";

    private static
    @Nonnull List<Salary> selectAllByEmpNoUsingNamedQuery(final @Nonnull EntityManager entityManager, final int empNo,
                                                          final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createNamedQuery("Salary.selectAllByEmpNo", Salary.class)
                .setParameter(PARAMETER_EMP_NO, empNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<Salary> selectAllByEmpNoUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                             final int empNo, final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createQuery(
                        """
                                SELECT s
                                FROM Salary AS s
                                WHERE s.empNo = :%1$s
                                ORDER BY s.fromDate DESC""".formatted(PARAMETER_EMP_NO),
                        Salary.class
                )
                .setParameter(PARAMETER_EMP_NO, empNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<Salary> selectAllByEmpNoUsingCriteriaApi(final @Nonnull EntityManager entityManager, final int empNo,
                                                           final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Salary.class);
        final var root = query.from(Salary.class);                                           // FROM Salary AS e
        query.select(root);                                                                  // SELECT e
        query.where(builder.equal(root.get(Salary_.empNo), empNo));                          // WHERE e.empNo = :empNo
        query.orderBy(builder.desc(root.get(Salary_.fromDate)));                             // ORDER BY e.fromDate DESC
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Salary> findAllByEmpNo(final @Nonnull EntityManager entityManager, final int empNo,
                                       final @Nullable Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllByEmpNoUsingNamedQuery(entityManager, empNo, maxResults);
            case 1 -> selectAllByEmpNoUsingQueryLanguage(entityManager, empNo, maxResults);
            default -> selectAllByEmpNoUsingCriteriaApi(entityManager, empNo, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("selectAllByEmpNoUsingNamedQuery")
    @Nested
    class SelectAllByEmpNoUsingNamedQueryIT {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 0;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNoUsingNamedQuery(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 10001;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNoUsingNamedQuery(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all)
                    .isNotEmpty()
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                    .extracting(Salary::getEmpNo)
                    .containsOnly(empNo);
        }
    }

    @DisplayName("selectAllByEmpNoUsingQueryLanguage")
    @Nested
    class SelectAllByEmpNoUsingQueryLanguageIT {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 0;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNoUsingQueryLanguage(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 10001;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNoUsingQueryLanguage(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all)
                    .isNotEmpty()
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                    .extracting(Salary::getEmpNo)
                    .containsOnly(empNo);
        }
    }

    @DisplayName("selectAllByEmpNoUsingCriteriaApi")
    @Nested
    class SelectAllByEmpNoUsingCriteriaApiIT {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 0;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNoUsingCriteriaApi(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 10001;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNoUsingCriteriaApi(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all)
                    .isNotEmpty()
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                    .extracting(Salary::getEmpNo)
                    .containsOnly(empNo);
        }
    }
}
