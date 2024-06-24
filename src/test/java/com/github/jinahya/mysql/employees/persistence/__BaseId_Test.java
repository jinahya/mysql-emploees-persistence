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

import java.util.Objects;

import static org.mockito.Mockito.spy;

abstract class __BaseId_Test<ID extends _BaseId> {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS
    __BaseId_Test(final Class<ID> idClass) {
        super();
        this.idClass = Objects.requireNonNull(idClass, "idClass is null");
    }

    // --------------------------------------------------------------------------------------------------------- idClass
    ID newIdSpy() {
        return spy(newIdInstance());
    }

    ID newIdInstance() {
        try {
            final var constructor = idClass.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to instantiate " + idClass, roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<ID> idClass;
}
