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
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.metamodel.Metamodel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.WeldJunit5AutoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An abstract base class for testing persistence context.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@AddBeanClasses({
        __PersistenceProducer.class
})
@ExtendWith(WeldJunit5AutoExtension.class)
@Slf4j
abstract class __PersistenceIT {

    // ------------------------------------------------------------------------------------------ STATIC-FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    __PersistenceIT() {
        super();
    }

    // --------------------------------------------------------------------------------------------------- entityManager
    final <R> R applyMetamodel(final Function<? super Metamodel, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyEntityManager(em -> function.apply(em.getMetamodel()));
    }

    final <R> R applyEntityManagerFactory(final Function<EntityManagerFactory, ? extends R> function) {
        return applyEntityManager(em -> function.apply(em.getEntityManagerFactory()));
    }

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

    private <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function,
                                                  final Consumer<? super EntityTransaction> consumer) {
        return applyEntityManager(em -> __PersistenceUtils.applyInTransaction(em, function, consumer));
    }

    final <R> R applyEntityManagerInTransaction(final Function<? super EntityManager, ? extends R> function) {
        return applyEntityManagerInTransaction(function, EntityTransaction::commit);
    }

    final <R> R applyEntityManagerInTransactionAndRollback(
            final Function<? super EntityManager, ? extends R> function) {
        return applyEntityManagerInTransaction(function, EntityTransaction::rollback);
    }

    // -----------------------------------------------------------------------------------------------------------------
    final <R> R applyConnection(final @Nonnull Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        // https://wiki.eclipse.org/EclipseLink/Examples/JPA/EMAPI#Getting_a_JDBC_Connection_from_an_EntityManager
        return applyEntityManagerInTransaction(em -> __JdbcUtils.applyUnwrappedConnection(em, function));
    }

    final <R> R applyConnectionInTransaction(final @Nonnull Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        if (ThreadLocalRandom.current().nextBoolean()) {
            return applyEntityManagerInTransaction(em -> __JdbcUtils.applyUnwrappedConnection(em, function));
        }
        return applyEntityManager(em -> __JdbcUtils.applyUnwrappedConnectionInTransaction(em, function));
    }

    final <R> R applyConnectionInTransactionAndRollback(
            final @Nonnull Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        if (ThreadLocalRandom.current().nextBoolean()) {
            return applyEntityManagerInTransactionAndRollback(
                    em -> __JdbcUtils.applyUnwrappedConnection(em, function));
        }
        return applyEntityManager(em -> __JdbcUtils.applyUnwrappedConnectionInTransactionAndRollback(em, function));
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * An injected instance of {@link EntityManager}.
     */
    @Inject
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private EntityManager entityManager;
}
