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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeptEmp_Constants_IT
        extends DeptEmp__IT {

    @Test
    void ATTRIBUTE_NAME_EMP_NO__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_EMP_NO)
                .isEqualTo(DeptEmp_.empNo.getName());
    }

    @Test
    void ATTRIBUTE_NAME_EMPLOYEE__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_EMPLOYEE)
                .isEqualTo(DeptEmp_.employee.getName());
    }

    @Test
    void ATTRIBUTE_DEPT_NO__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_DEPT_NO)
                .isEqualTo(DeptEmp_.deptNo.getName());
    }

    @Test
    void ATTRIBUTE_NAME_DEPARTMENT__() {
        assertThat(DeptEmp.ATTRIBUTE_NAME_DEPARTMENT)
                .isEqualTo(DeptEmp_.department.getName());
    }
}
