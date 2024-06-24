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

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class CurrentDeptEmp_Basic_Test
        extends _BaseEntity_Basic_Test<CurrentDeptEmp, CurrentDeptEmpId> {

    CurrentDeptEmp_Basic_Test() {
        super(CurrentDeptEmp.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<CurrentDeptEmp> equals__(
            final SingleTypeEqualsVerifierApi<CurrentDeptEmp> verifierApi) {
        return super.equals__(verifierApi)
                    .suppress(Warning.SURROGATE_KEY)
                    .withPrefabValues(DeptEmp.class, DeptEmp.of(0, "1"), DeptEmp.of(1, "2"))
//                .withIgnoredFields("deptEmp")
                ;
    }
}
