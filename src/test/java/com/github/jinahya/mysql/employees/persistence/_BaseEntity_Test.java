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

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
abstract class _BaseEntity_Test<ENTITY extends _BaseEntity<ID>, ID extends Serializable>
        extends __BaseEntity__Test<ENTITY, ID> {

    /**
     * Creates a new instance for testing specified entity class.
     *
     * @param entityClass the entity class to test
     * @see #entityClass
     */
    _BaseEntity_Test(final Class<ENTITY> entityClass) {
        super(entityClass);
    }
}
