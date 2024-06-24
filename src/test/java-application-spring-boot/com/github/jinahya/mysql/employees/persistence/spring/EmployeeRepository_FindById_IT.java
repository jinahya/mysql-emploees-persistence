package com.github.jinahya.mysql.employees.persistence.spring;

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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

import static com.github.jinahya.mysql.employees.persistence._BaseEntity_Assertions.assertBaseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeRepository_FindById_IT
        extends EmployeeRepository__IT {

    static List<Integer> selectEmpNoList(final EntityManager entityManager, final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e.empNo
                                FROM Employee AS e
                                ORDER BY e.empNo ASC""",
                        Integer.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<Integer> getEmpNoList() {
        return selectEmpNoList(entityManager(), 32);
    }

    @MethodSource({"getEmpNoList"})
    @ParameterizedTest
    void findById__(final int empNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var found = repositoryInstance().findById(empNo);
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found).hasValueSatisfying(v -> {
            assertThat(v.getEmpNo()).isEqualTo(empNo);
            assertBaseEntity(v).hasEmpNo(empNo);
        });
    }
}
