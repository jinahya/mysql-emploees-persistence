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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class Employee_GenderConverter_Test
        extends Employee__Test {

    @DisplayName("convertToDatabaseColumn(attribute)")
    @Nested
    class ConvertToDatabaseColumnTest {

        @DisplayName("(null)null")
        @Test
        void _Null_Null() {
            final var instance = new Employee.GenderConverter();
            final var databaseColumn = instance.convertToDatabaseColumn(null);
            assertThat(databaseColumn).isNull();
        }

        @DisplayName("(!null)!null")
        @EnumSource(Employee.Gender.class)
        @ParameterizedTest
        void __(final Employee.Gender gender) {
            final var instance = new Employee.GenderConverter();
            final var databaseColumn = instance.convertToDatabaseColumn(gender);
            assertThat(databaseColumn).isEqualTo(gender.getColumnValue());
        }
    }

    @DisplayName("convertToEntityAttribute(dbData)")
    @Nested
    class ConvertToEntityAttribute {

        @DisplayName("(null)null")
        @Test
        void _Null_Null() {
            final var instance = new Employee.GenderConverter();
            final var entityAttribute = instance.convertToEntityAttribute(null);
            assertThat(entityAttribute).isNull();
        }

        @DisplayName("(!null)!null")
        @EnumSource(Employee.Gender.class)
        @ParameterizedTest
        void __(final Employee.Gender gender) {
            final var instance = new Employee.GenderConverter();
            final var entityAttribute = instance.convertToEntityAttribute(gender.getColumnValue());
            assertThat(entityAttribute).isEqualTo(gender);
        }
    }
}
