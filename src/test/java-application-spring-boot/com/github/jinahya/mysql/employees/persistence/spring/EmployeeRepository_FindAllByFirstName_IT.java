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

import com.github.jinahya.mysql.employees.persistence.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class EmployeeRepository_FindAllByFirstName_IT
        extends EmployeeRepository__IT {

    private List<Arguments> getFirstNameAndCountArgumentsList() {
        return entityManager()
                .createQuery("""
                                     SELECT e.firstName, COUNT(e) AS c
                                     FROM Employee AS e
                                     GROUP BY e.firstName
                                     ORDER BY c DESC, e.firstName ASC
                                     """,
                             Object[].class)
                .setMaxResults(8)
                .getResultList()
                .stream()
                .map(Arguments::of)
                .toList();
    }

    @MethodSource({"getFirstNameAndCountArgumentsList"})
    @ParameterizedTest
    void __(final String firstName, final long count) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = repositoryInstance().findAllByFirstName(firstName, Pageable.unpaged());
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all.getContent())
                .hasSizeLessThanOrEqualTo(Math.toIntExact(count))
                .isSortedAccordingTo(Comparator.comparing(Employee::getFirstName))
                .extracting(Employee::getFirstName)
                .containsOnly(firstName);
    }
}
