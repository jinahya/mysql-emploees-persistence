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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class _BaseEntityRepositoryConstantsTest {

    @DisplayName("MIN_PARAM_VALUE_MONTH == JANUARY.value")
    @Test
    void MIN_PARAM_VALUE_MONTH_JANUARY_() {
        assertThat(_BaseEntityRepositoryConstants.PARAM_VALUE_MONTH_MIN)
                .isEqualTo(Month.JANUARY.getValue());
    }

    @DisplayName("MAX_PARAM_VALUE_MONTH == DECEMBER.value")
    @Test
    void MAX_PARAM_VALUE_MONTH_DECEMBER_() {
        assertThat(_BaseEntityRepositoryConstants.PARAM_VALUE_MONTH_MAX)
                .isEqualTo(Month.DECEMBER.getValue());
    }
}
