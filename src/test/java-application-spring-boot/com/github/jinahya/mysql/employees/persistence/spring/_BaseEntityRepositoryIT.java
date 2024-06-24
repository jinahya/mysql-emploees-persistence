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
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

@SpringBootTest
abstract class _BaseEntityRepositoryIT<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable>
        extends __BaseEntityRepositoryTestBase<REPOSITORY, ENTITY, ID> {

    // -----------------------------------------------------------------------------------------------------------------
    _BaseEntityRepositoryIT(final Class<REPOSITORY> repositoryClass) {
        super(repositoryClass);
    }

    // ---------------------------------------------------------------------------------------------- repositoryInstance

    // --------------------------------------------------------------------------------------------------- entityManager

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PACKAGE)
    private REPOSITORY repositoryInstance;

    @Autowired
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PACKAGE)
    private EntityManager entityManager;
}
