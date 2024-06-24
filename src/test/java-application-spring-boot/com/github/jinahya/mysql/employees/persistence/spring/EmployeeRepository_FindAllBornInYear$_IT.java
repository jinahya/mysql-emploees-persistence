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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
abstract class EmployeeRepository_FindAllBornInYear$_IT
        extends EmployeeRepository__IT {

    static List<Integer> selectDistinctBirthYearList(final EntityManager entityManager,
                                                     final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT DISTINCT EXTRACT(YEAR FROM e.birthDate) AS birthYear
                                FROM Employee AS e
                                ORDER BY birthYear ASC""",
                        Integer.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }
}
