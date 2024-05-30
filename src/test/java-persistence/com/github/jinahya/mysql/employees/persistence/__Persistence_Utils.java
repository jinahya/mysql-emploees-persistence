package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.metamodel.EntityType;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
final class __Persistence_Utils {

    static <R> R applyInTransaction(final EntityManager entityManager,
                                    final Function<? super EntityManager, ? extends R> function,
                                    final Consumer<? super EntityTransaction> consumer) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        if (entityManager.isJoinedToTransaction()) {
            throw new IllegalArgumentException("already joined to a transaction: " + entityManager);
        }
        Objects.requireNonNull(function, "function is null");
        final var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            final var result = function.apply(entityManager);
            consumer.accept(transaction);
            return result;
        } catch (final Exception e) {
            transaction.rollback();
            log.debug("rolled-back: " + transaction);
            throw new RuntimeException(e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns {@link EntityType#getName() entityName} of specified class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the entity class whose {@link EntityType#getName() entityName} is returned.
     * @param <ENTITY>      entity type parameter.
     * @return {@link EntityType#getName() entityName} of the {@code entityClass}.
     */
    static <ENTITY extends _BaseEntity<?>>
    @Nonnull String getEntityName(final @Nonnull EntityManager entityManager,
                                  final @Nonnull Class<ENTITY> entityClass) {
        return entityManager.getMetamodel().entity(entityClass).getName();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static <ENTITY extends _BaseEntity<?>>
    @PositiveOrZero long selectCountUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                       final @Nonnull Class<ENTITY> entityClass) {
        final var entityName = getEntityName(entityManager, entityClass);
        return entityManager
                .createQuery("SELECT COUNT(e) FROM %1$s AS e".formatted(entityName), Long.class)
                .getSingleResult();
    }

    private static <ENTITY extends _BaseEntity<?>>
    @PositiveOrZero long selectCountUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                     final @Nonnull Class<ENTITY> entityClass) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Long.class);
        final var from = query.from(entityClass);
        query.select(builder.count(from));
        return entityManager.createQuery(query).getSingleResult();
    }

    static <ENTITY extends _BaseEntity<?>>
    @PositiveOrZero long selectCount(final @Nonnull EntityManager entityManager,
                                     final @Nonnull Class<ENTITY> entityClass) {
        return ThreadLocalRandom.current().nextBoolean()
                ? selectCountUsingQueryLanguage(entityManager, entityClass)
                : selectCountUsingCriteriaApi(entityManager, entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static <ENTITY extends _BaseEntity<?>>
    @Nonnull Optional<ENTITY> selectRandom_QueryLanguage(final @Nonnull EntityManager entityManager,
                                                         final @Nonnull Class<ENTITY> entityClass) {
        final var entityName = getEntityName(entityManager, entityClass);
        final var count = selectCount(entityManager, entityClass);
        if (count == 0L) {
            return Optional.empty();
        }
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return Optional.of(
                entityManager
                        .createQuery("SELECT e FROM %1$s AS e".formatted(entityName), entityClass)
                        .setFirstResult(startPosition)
                        .setMaxResults(1)
                        .getSingleResult()
        );
    }

    private static <ENTITY extends _BaseEntity<?>>
    @Nonnull Optional<ENTITY> selectRandom_CriteriaApi(final @Nonnull EntityManager entityManager,
                                                       final @Nonnull Class<ENTITY> entityClass) {
        final var count = selectCount(entityManager, entityClass);
        if (count == 0L) {
            return Optional.empty();
        }
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(entityClass);
        final var from = query.from(entityClass);
        query.select(from);
        return Optional.of(
                entityManager
                        .createQuery(query)
                        .setFirstResult(startPosition)
                        .getSingleResult()
        );
    }

    static <ENTITY extends _BaseEntity<?>>
    @Nonnull Optional<ENTITY> selectRandom(final @Nonnull EntityManager entityManager,
                                           final @Nonnull Class<ENTITY> entityClass) {
        return ThreadLocalRandom.current().nextBoolean()
                ? selectRandom_QueryLanguage(entityManager, entityClass)
                : selectRandom_CriteriaApi(entityManager, entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private __Persistence_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
