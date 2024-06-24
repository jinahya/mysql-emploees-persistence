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
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Employee_SelectMaxEmpNo_IT
        extends _BaseEntityIT<Employee, Integer> {

    private static Optional<Integer> selectMaxEmpNo1(final EntityManager entityManager) {
        try {
            return Optional.of(
                    entityManager
                            .createNamedQuery("Employee.selectMaxEmpNo", Integer.class)
                            .getSingleResult()
            );
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    private static Optional<Integer> selectMaxEmpNo2(final EntityManager entityManager) {
        try {
            return Optional.of(
                    entityManager
                            .createQuery("SELECT MAX(e.empNo) FROM Employee AS e", Integer.class)
                            .getSingleResult()
            );
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    private static Optional<Integer> selectMaxEmpNo3(final EntityManager entityManager) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Integer.class);
        final var root = query.from(Employee.class);
        query.select(builder.max(root.get(Employee_.empNo)));
        try {
            return Optional.of(
                    entityManager.createQuery(query).getSingleResult()
            );
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    static Optional<Integer> selectMaxEmpNo(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectMaxEmpNo1(entityManager);
            case 1 -> selectMaxEmpNo2(entityManager);
            default -> selectMaxEmpNo3(entityManager);
        };
    }

    static Integer getNextEmpNo(final EntityManager entityManager) {
        return selectMaxEmpNo(entityManager).orElse(0) + 1;
    }

    // -----------------------------------------------------------------------------------------------------------------
    Employee_SelectMaxEmpNo_IT() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectMaxEmpNo1__() {
        final var maxEmpNo = applyEntityManager(
                Employee_SelectMaxEmpNo_IT::selectMaxEmpNo1
        );
        assertThat(maxEmpNo).satisfiesAnyOf(
                o -> assertThat(o).isEmpty(),
                o -> assertThat(o).hasValueSatisfying(men -> {
                    // empty
                })
        );
    }

    @Test
    void selectMaxEmpNo2__() {
        final var maxEmpNo = applyEntityManager(
                Employee_SelectMaxEmpNo_IT::selectMaxEmpNo2
        );
        assertThat(maxEmpNo).satisfiesAnyOf(
                o -> assertThat(o).isEmpty(),
                o -> assertThat(o).hasValueSatisfying(men -> {
                    // empty
                })
        );
    }

    @Test
    void selectMaxEmpNo3__() {
        final var maxEmpNo = applyEntityManager(
                Employee_SelectMaxEmpNo_IT::selectMaxEmpNo3
        );
        assertThat(maxEmpNo).satisfiesAnyOf(
                o -> assertThat(o).isEmpty(),
                o -> assertThat(o).hasValueSatisfying(men -> {
                    // empty
                })
        );
    }
}
