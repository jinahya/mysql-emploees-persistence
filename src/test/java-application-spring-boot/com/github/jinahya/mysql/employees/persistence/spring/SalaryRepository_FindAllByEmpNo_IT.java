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
import com.github.jinahya.mysql.employees.persistence.Salary;
import com.github.jinahya.mysql.employees.persistence.Salary_;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class SalaryRepository_FindAllByEmpNo_IT
        extends SalaryRepository__IT {

    private IntStream getEmpNoStream() {
        return employeeRepository
                .findAll(PageRequest.of(0, 8, Sort.by(Sort.Order.asc(Employee_.empNo.getName()))))
                .stream()
                .mapToInt(Employee::getEmpNo);
    }

    @MethodSource({"getEmpNoStream"})
    @ParameterizedTest
    void __(final int empNo) {
        // ------------------------------------------------------------------------------------------------------- given
        final var size = ThreadLocalRandom.current().nextInt(8) + 1;
        final var sort = Sort.by(Sort.Order.asc(Salary_.fromDate.getName()));
        for (var pageable = PageRequest.of(0, size, sort); ; pageable = pageable.next()) {
            // ---------------------------------------------------------------------------------------------------- when
            final var found = repositoryInstance().findAllByEmpNo(empNo, pageable);
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(found.getContent())
                    .hasSizeLessThanOrEqualTo(size)
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate))
                    .extracting(Salary::getEmpNo)
                    .containsOnly(empNo);
            // break
            if (!found.hasNext() || pageable.getPageNumber() > 2) {
                break;
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    private EmployeeRepository employeeRepository;
}
