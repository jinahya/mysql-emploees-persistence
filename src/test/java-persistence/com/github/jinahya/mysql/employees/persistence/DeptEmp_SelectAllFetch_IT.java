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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("selectAllFetch")
@Slf4j
class DeptEmp_SelectAllFetch_IT
        extends DeptEmp__IT {

    // -----------------------------------------------------------------------------------------------------------------
    private static
    @Nonnull List<DeptEmp> selectAllUsingNamedQuery(final @Nonnull EntityManager entityManager,
                                                    final @Nullable Integer maxResults) {
        return entityManager
                .createNamedQuery("DeptEmp.selectAllFetch", DeptEmp.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                       final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM DeptEmp AS e
                                JOIN FETCH e.department
                                JOIN FETCH e.employee
                                ORDER BY e.empNo ASC, e.deptNo ASC""",
                        DeptEmp.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                     final @Nullable Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptEmp.class);
        // @formatter:off
        final var root = query.from(DeptEmp.class);                                       // FROM DeptEmp AS e
        query.select(root);                                                               // SELECT e
        root.fetch(DeptEmp_.department);                                                  //     JOIN FETCH e.department
        root.fetch(DeptEmp_.employee);                                                    //     JOIN FETCH e.employee
        query.orderBy(                                                                    // ORDER BY
                builder.asc(root.get(DeptEmp_.empNo)),                                    //     e.empNo ASC
                builder.asc(root.get(DeptEmp_.deptNo))                                    //     e.deptNo ASC
        );
        // @formatter:on
        return entityManager.createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<DeptEmp> selectAll(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllUsingNamedQuery(entityManager, maxResults);
            case 1 -> selectAllUsingQueryLanguage(entityManager, maxResults);
            default -> selectAllUsingCriteriaApi(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void verify(final int maxResults, final List<DeptEmp> all) {
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(DeptEmp_SelectAll__IT.COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO)
                .hasSizeLessThanOrEqualTo(maxResults);
    }

    @Test
    void selectAllUsingNamedQuery__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = 4;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingNamedQuery(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, all);
    }

    @Test
    void selectAllUsingQueryLanguage__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = 4;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingQueryLanguage(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, all);
    }

    @Test
    void selectAllUsingCriteriaApi__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = 4;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingCriteriaApi(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, all);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAll__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = 4;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, all);
        all.forEach(e -> {
            final var department = e.getDepartment();
            log.debug("department: {}", department);
            final var employee = e.getEmployee();
            log.debug("employee: {}", employee);
        });
    }
}
