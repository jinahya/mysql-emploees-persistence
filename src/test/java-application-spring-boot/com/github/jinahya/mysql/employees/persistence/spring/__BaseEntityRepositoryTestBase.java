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

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.util.Objects;

abstract class __BaseEntityRepositoryTestBase<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable> {

    // -----------------------------------------------------------------------------------------------------------------
    __BaseEntityRepositoryTestBase(final Class<REPOSITORY> repositoryClass) {
        super();
        this.repositoryClass = Objects.requireNonNull(repositoryClass, "repositoryClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void doNothing__() {
    }

    // ------------------------------------------------------------------------------------------------- repositoryClass
    @SuppressWarnings({"unchecked"})
    Class<ENTITY> entityClass() {
        return (Class<ENTITY>) Objects.requireNonNull(
                ResolvableType
                        .forClass(repositoryClass)
                        .as(_BaseEntityRepository.class)
                        .getGeneric(0)
                        .resolve()
        );
    }

    @SuppressWarnings({"unchecked"})
    Class<ID> idClass() {
        return (Class<ID>) Objects.requireNonNull(
                ResolvableType
                        .forClass(repositoryClass)
                        .as(_BaseEntityRepository.class)
                        .getGeneric(2)
                        .resolve()
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<REPOSITORY> repositoryClass;
}
