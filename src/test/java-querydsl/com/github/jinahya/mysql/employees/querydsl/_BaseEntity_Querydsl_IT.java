package com.github.jinahya.mysql.employees.querydsl;

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
import com.github.jinahya.mysql.employees.persistence._PersistenceProducer;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.metamodel.EntityType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.WeldJunit5AutoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@AddBeanClasses({
        _PersistenceProducer.class
})
@ExtendWith(WeldJunit5AutoExtension.class)
abstract class _BaseEntity_Querydsl_IT<ENTITY extends _BaseEntity<ID>, ID extends Serializable> {

    _BaseEntity_Querydsl_IT(final Class<ENTITY> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void doNothing() {
        // empty
    }

    // ----------------------------------------------------------------------------------------------------- entityClass
    Class<ID> idClass() {
        return _BaseEntityTestUtils.idClass(entityClass);
    }

    // --------------------------------------------------------------------------------------------------- entityManager

    /**
     * Applies {@link #entityManager} to specified function, and return the result.
     *
     * @param function the function applies with the entity manager.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    final <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        return Objects.requireNonNull(function, "function is null").apply(entityManager);
    }

    final <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function,
                                                final Consumer<? super EntityTransaction> consumer) {
        return applyEntityManager(em -> {
            final var transaction = em.getTransaction();
            transaction.begin();
            try {
                return function.apply(em);
            } finally {
                consumer.accept(transaction);
            }
        });
    }

    final <R> R applyEntityManagerInTransactionAndCommit(final Function<? super EntityManager, ? extends R> function) {
        return applyEntityManagerInTransaction(function, EntityTransaction::commit);
    }

    final <R> R applyEntityManagerInTransactionAndRollback(
            final Function<? super EntityManager, ? extends R> function) {
        return applyEntityManagerInTransaction(function, EntityTransaction::rollback);
    }

    final <R> R applyQuery(final Function<? super JPAQuery<ENTITY>, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyEntityManager(
                em -> function.apply(new JPAQuery<>(em))
        );
    }

    final <R> R applyQueryInTransaction(final Function<? super JPAQuery<ENTITY>, ? extends R> function,
                                        final Consumer<? super EntityTransaction> consumer) {
        return applyEntityManagerInTransaction(
                em -> function.apply(new JPAQuery<>(em)),
                consumer
        );
    }

    final <R> R applyQueryInTransactionAndCommit(final Function<? super JPAQuery<ENTITY>, ? extends R> function) {
        return applyEntityManagerInTransaction(
                em -> function.apply(new JPAQuery<>(em)),
                EntityTransaction::commit
        );
    }

    final <R> R applyQueryInTransactionAndRollback(final Function<? super JPAQuery<ENTITY>, ? extends R> function) {
        return applyEntityManagerInTransaction(
                em -> function.apply(new JPAQuery<>(em)),
                EntityTransaction::rollback
        );
    }

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
            final var metamodel = entityManager.getMetamodel();
            final var entityType = metamodel.entity(entityClass);
            entityName = entityType.getName();
        }
        return entityName;
    }

    /**
     * Returns the identifier of specified entity.
     *
     * @param entity the entity.
     * @return the identifier of the {@code entity}.
     * @see jakarta.persistence.PersistenceUnitUtil#getIdentifier(Object)
     */
    final ID identifier(final ENTITY entity) {
        return idClass().cast(
                entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<ENTITY> entityClass;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An injected instance of {@link EntityManager}.
     */
    @Inject
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private EntityManager entityManager;

    /**
     * A cached value for the {@link #entityName()} method.
     */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String entityName;
}
