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

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

/**
 * An abstract base class for testing subclasses of {@link _BaseEntity} class.
 *
 * @param <ENTITY> entity type parameter
 * @param <ID>     id type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
abstract class _BaseEntityIT<ENTITY extends _BaseEntity<ID>, ID extends Serializable>
        extends __PersistenceIT {

    // ------------------------------------------------------------------------------------------ STATIC-FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance for testing specified entity class.
     *
     * @param entityClass the entity class to test.
     * @see #entityClass
     */
    _BaseEntityIT(final Class<ENTITY> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        this.idClass = _BaseEntityTestUtils.idClass(this.entityClass);
    }

//    // --------------------------------------------------------------------------------------------------- entityManager
//
//    <R> R applyMetamodel(final Function<? super Metamodel, ? extends R> function) {
//        Objects.requireNonNull(function, "function is null");
//        return applyEntityManager(em -> function.apply(em.getMetamodel()));
//    }
//
//    /**
//     * Returns the {@link Metamodel#managedType(Class) managedType} of the {@link #entityClass}.
//     *
//     * @return the {@link Metamodel#managedType(Class) managedType} of the {@link #entityClass}.
//     */
//    ManagedType<ENTITY> managedType() {
//        return applyMetamodel(m -> m.managedType(entityClass));
//    }
//
//    /**
//     * Applies {@link #entityManager} to specified function, and return the result.
//     *
//     * @param function the function applies with the entity manager.
//     * @param <R>      result type parameter
//     * @return the result of the {@code function}.
//     */
//    final <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
//        return Objects.requireNonNull(function, "function is null").apply(entityManager);
//    }
//
//    private <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function,
//                                                  final Consumer<? super EntityTransaction> consumer) {
//        return applyEntityManager(em -> __Persistence_Utils.applyInTransaction(em, function, consumer));
//    }
//
//    final <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function) {
//        return applyEntityManagerInTransaction(function, EntityTransaction::commit);
//    }
//
//    final <R> R applyEntityManagerInTransactionAndRollback(
//            final Function<? super EntityManager, ? extends R> function) {
//        return applyEntityManagerInTransaction(function, EntityTransaction::rollback);
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//    final <R> R applyConnection(final @Nonnull Function<? super Connection, ? extends R> function) {
//        Objects.requireNonNull(function, "function is null");
//        // https://wiki.eclipse.org/EclipseLink/Examples/JPA/EMAPI#Getting_a_JDBC_Connection_from_an_EntityManager
//        return applyEntityManagerInTransaction(em -> __Jdbc_Utils.applyUnwrappedConnection(em, function));
//    }
//
//    final <R> R applyConnectionInTransaction(final @Nonnull Function<? super Connection, ? extends R> function) {
//        Objects.requireNonNull(function, "function is null");
//        if (ThreadLocalRandom.current().nextBoolean()) {
//            return applyEntityManagerInTransaction(em -> __Jdbc_Utils.applyUnwrappedConnection(em, function));
//        }
//        return applyEntityManager(em -> __Jdbc_Utils.applyUnwrappedConnectionInTransaction(em, function));
//    }
//
//    final <R> R applyConnectionInTransactionAndRollback(
//            final @Nonnull Function<? super Connection, ? extends R> function) {
//        Objects.requireNonNull(function, "function is null");
//        if (ThreadLocalRandom.current().nextBoolean()) {
//            return applyEntityManagerInTransactionAndRollback(
//                    em -> __Jdbc_Utils.applyUnwrappedConnection(em, function));
//        }
//        return applyEntityManager(em -> __Jdbc_Utils.applyUnwrappedConnectionInTransactionAndRollback(em, function));
//    }

    // ------------------------------------------------------------------------------------------------------ entityName

    /**
     * Returns the {@link EntityType#getName() entity name} of the {@link #entityClass}.
     *
     * @return the {@link EntityType#getName() entity name} of the {@link #entityClass}
     * @see EntityManager#getMetamodel()
     * @see jakarta.persistence.metamodel.Metamodel#entity(Class)
     * @see EntityType#getName()
     */
    final String entityName() {
        if (entityName == null) {
            entityName = applyMetamodel(m -> {
                final var entityType = m.entity(entityClass);
                return entityType.getName();
            });
        }
        return entityName;
    }

    /**
     * Returns the identifier of specified entity instance.
     *
     * @param entity the entity instance whose identifier is returned.
     * @return the identifier of the {@code entity}.
     * @see jakarta.persistence.PersistenceUnitUtil#getIdentifier(Object)
     */
    final @Nonnull ID id(final @Nonnull ENTITY entity) {
        Objects.requireNonNull(entity, "entity is null");
        return applyEntityManager(em -> {
            return idClass.cast(
                    em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity));
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
//
//    /**
//     * An injected instance of {@link EntityManager}.
//     */
//    @Inject
//    @Accessors(fluent = true)
//    @Setter(AccessLevel.NONE)
//    @Getter(AccessLevel.NONE)
//    private EntityManager entityManager;

    final Class<ENTITY> entityClass;

    final Class<ID> idClass;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A cached value for the {@link #entityName()} method.
     */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String entityName;
}
