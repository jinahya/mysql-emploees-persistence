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
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class DeptManager__IT
        extends _BaseEntityIT<DeptManager, DeptManagerId> {

    @Test
    void ATTRIBUTE_NAME_FROM_DATE__() {
        assertThat(DeptManager.ATTRIBUTE_NAME_FROM_DATE)
                .isEqualTo(DeptManager_.fromDate.getName());
    }

    private static Department findByDeptNo1(final EntityManager entityManager, final String deptNo) {
        final var query = entityManager.createQuery(
                "SELECT e FROM Department AS e WHERE e.deptNo = :deptNo",
                Department.class
        );
        query.setParameter("deptNo", deptNo);
        query.getSingleResult();
        return query.getSingleResult();
    }

    private static Department findByDeptNo2(final EntityManager entityManager, final String deptNo) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Department.class);
        final var root = query.from(Department.class);                                       // FROM Department AS e
        query.select(root);                                                                  // SELECT e
        query.where(builder.equal(root.get("deptNo"), deptNo));                              // WHERE e.deptNo = :deptNo
        return entityManager.createQuery(query).getSingleResult();
    }

    static Department findByDeptNo(final EntityManager entityManager, final String deptNo) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(deptNo, "deptNo is null");
        if (ThreadLocalRandom.current().nextBoolean()) {
            return findByDeptNo1(entityManager, deptNo);
        }
        return findByDeptNo2(entityManager, deptNo);
    }

    DeptManager__IT() {
        super(DeptManager.class);
    }
}
