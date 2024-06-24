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
import com.github.jinahya.mysql.employees.persistence.Employee_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * A class for testing {@link EmployeeRepository#findAllByGender(Employee.Gender, Pageable)} method.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class EmployeeRepository_FindAllByGender_IT
        extends EmployeeRepository__IT {

    @EnumSource(Employee.Gender.class)
    @ParameterizedTest
    void __(final Employee.Gender gender) {
        final var page = 0;
        final var size = 8;
        final var sort = Sort.by(Sort.Order.asc(Employee_.empNo.getName()));
        final var pageable = PageRequest.of(page, size, sort);
        // -------------------------------------------------------------------------------------------------------- when
        final var all = repositoryInstance().findAllByGender(gender, pageable);
        // -------------------------------------------------------------------------------------------------------- then
        // TODO: verify!
    }
}
