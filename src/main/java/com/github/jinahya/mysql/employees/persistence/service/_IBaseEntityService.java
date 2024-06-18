package com.github.jinahya.mysql.employees.persistence.service;

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
    Optional<ENTITY> find(@NotNull @Nonnull ID id);

    /**
     * Persists specified entity, and returns the entity.
     *
     * @param entity the entity to persist.
     * @return given {@code entity}.
     */
    ENTITY persist(@Valid @NotNull @Nonnull ENTITY entity);
}
