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

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class DeptManager_SelectAll_IT
        extends _BaseEntityIT<DeptManager, DeptManagerId> {

    private static List<DeptManager> selectAll1(final EntityManager entityManager, final Integer maxResults) {
        return entityManager
                .createNamedQuery("DeptManager.selectAll", DeptManager.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<DeptManager> selectAll2(final EntityManager entityManager, final Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM DeptManager AS e
                                ORDER BY e.id.empNo ASC, e.id.deptNo ASC
                                """,
                        DeptManager.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<DeptManager> selectAll3(final EntityManager entityManager, final Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptManager.class);
        final var root = query.from(DeptManager.class);                                         // SELECT e
        final var id = root.get(DeptManager_.id);                                               // FROM DeptManager AS e
        query.select(root);
        query.orderBy(                                                                          // ORDER BY
                                                                                                builder.asc(
                                                                                                        id.get(DeptManagerId_.empNo)),
                                                                                                //   e.id.empNo ASC
                                                                                                builder.asc(
                                                                                                        id.get(DeptManagerId_.deptNo))
                                                                                                //   e.id.deptNo ASC
        );
        return entityManager.createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    DeptManager_SelectAll_IT() {
        super(DeptManager.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    void selectAll1__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll1(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .hasSizeLessThanOrEqualTo(maxResults)
                .extracting(this::id)
                .isSorted();
    }
}
