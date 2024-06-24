package com.github.jinahya.mysql.employees.persistence.service;

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

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import com.github.jinahya.mysql.employees.persistence._BaseEntityTestUtils;

import java.io.Serializable;
import java.util.Objects;

abstract class __IBaseEntityServiceTest<
        SERVICE extends _IBaseEntityService<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable> {

    __IBaseEntityServiceTest(final Class<SERVICE> serviceClass, final Class<ENTITY> entityClass) {
        super();
        this.serviceClass = Objects.requireNonNull(serviceClass, "serviceClass is null");
        this.entityClass = entityClass;
        idClass = _BaseEntityTestUtils.idClass(entityClass);
    }

    final Class<SERVICE> serviceClass;

    final Class<ENTITY> entityClass;

    final Class<ID> idClass;
}
