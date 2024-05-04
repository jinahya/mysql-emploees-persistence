package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.metamodel.EntityType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.WeldJunit5AutoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An abstract base class for testing subclasses of {@link _BaseEntity} class.
 *
 * @param <ENTITY> entity type parameter
 * @param <ID>     id type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@AddBeanClasses({
        _PersistenceProducer.class
})
@ExtendWith(WeldJunit5AutoExtension.class)
@Slf4j
abstract class _BaseEntityIT<ENTITY extends _BaseEntity<ID>, ID extends Serializable>
        extends __BaseEntity__Test<ENTITY, ID> {

    private static final Method CLOSE_METHOD;

    static {
        try {
            CLOSE_METHOD = EntityManager.class.getMethod("close");
        } catch (final NoSuchMethodException nsme) {
            throw new InstantiationError(nsme.getMessage());
        }
    }

    // ------------------------------------------------------------------------------------------ STATIC-FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance for testing specified entity class.
     *
     * @param entityClass the entity class to test.
     * @see #entityClass
     */
    _BaseEntityIT(final Class<ENTITY> entityClass) {
        super(entityClass);
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
        return applyEntityManager(em -> {
            try (var connection = __Lang_Utils.uncloseable(Connection.class, null, em.unwrap(Connection.class))) {
                return function.apply(connection);
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    final void acceptConnection(final @Nonnull Consumer<? super Connection> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");
        applyConnection(c -> {
            consumer.accept(c);
            return null;
        });
    }

    // ------------------------------------------------------------------------------------------------------ entityName

    /**
     * Returns the {@link EntityType#getName() entity name} of the {@link #entityClass}.
     *
     * @return the {@link EntityType#getName() entity name} of the {@link #entityClass}
     * @sesee EntityManager#getMetamodel()
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
    final ID id(final ENTITY entity) {
        return idClass().cast(
                entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity)
        );
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

    /**
     * A cached value for the {@link #entityName()} method.
     */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String entityName;
}
