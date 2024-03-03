package com.github.jinahya.mysql.employees.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

abstract class _BaseService<ENTITY extends _BaseEntity<ID>, ID extends Serializable> {

    _BaseService(final Class<ENTITY> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
    }

    // ----------------------------------------------------------------------------------------------------- entityClass

    final String entityName() {
        return _BaseEntityHelper.entityName(entityManager, entityClass);
    }

    /**
     * Returns the class of {@link ID}.
     *
     * @return the class of {@link ID}.
     */
    final Class<ID> idClass() {
        return _BaseEntityHelper.idClass(entityClass);
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
                entityManager
                        .getEntityManagerFactory()
                        .getPersistenceUnitUtil()
                        .getIdentifier(entity)
        );
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
        Objects.requireNonNull(function, "function is null");
        Objects.requireNonNull(consumer, "consumer is null");
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

    /**
     * The entity class of this service class.
     */
    final Class<ENTITY> entityClass;

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private EntityManager entityManager;
}
