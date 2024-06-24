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
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Optional;

/**
 * A base service interface for subclasses of {@link _BaseEntity}.
 *
 * @param <ENTITY> entity type parameter
 * @param <ID>     id type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@SuppressWarnings({
        "java:S114", // interface _IBase...
        "java:S119" // <ENTITY ...>
})
public interface _IBaseEntityService<ENTITY extends _BaseEntity<ID>, ID extends Serializable> {

    /**
     * Finds the entity identified by specified value.
     *
     * @param id the value to identify the entity.
     * @return an optional of identified entity; {@link Optional#empty() empty} when non identified.
     */
    @NotNull
    @Nonnull
    Optional<@Valid ENTITY> findEntity(@NotNull @Nonnull ID id);

    /**
     * Persists specified entity, and returns the entity.
     *
     * @param entity the entity to persist.
     * @return given {@code entity}.
     */
    @Valid
    @NotNull
    @Nonnull
    ENTITY persistEntity(@Valid @NotNull @Nonnull ENTITY entity);
}
