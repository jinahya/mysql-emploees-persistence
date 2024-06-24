package com.github.jinahya.mysql.employees.querydsl;

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

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.QDepartment;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class Department_Querydsl_SelectAll_IT
        extends Department_Querydsl__IT {

    @Test
    void selectAll__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var limit = ThreadLocalRandom.current().nextInt(32) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyQuery(q -> {
            return q.select(QDepartment.department)                                              // SELECT dept_no, ...
                    .from(QDepartment.department)                                                // FROM departments
                    .orderBy(QDepartment.department.deptNo.asc())                                // ORDER BY dept_no ASC
                    .limit(limit)                                                                // LIMIT ?,?
                    .fetch();
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .hasSizeLessThanOrEqualTo(limit)
                .isSortedAccordingTo(Comparator.comparing(Department::getDeptNo));
    }
}
