package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
final class _BaseEntityIT_Utils {

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
                .createQuery("SELECT COUNT(*) FROM %1$s" .formatted(entityName), Long.class)
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
    @Nonnull ENTITY selectRandomUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                   final @Nonnull Class<ENTITY> entityClass) {
        final var entityName = getEntityName(entityManager, entityClass);
        final var count = selectCount(entityManager, entityClass);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e FROM %1$s AS e" .formatted(entityName), entityClass)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult();
    }

    private static <ENTITY extends _BaseEntity<?>>
    @Nonnull ENTITY selectRandomUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                 final @Nonnull Class<ENTITY> entityClass) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(entityClass);
        final var from = query.from(entityClass);
        query.select(from);
        return entityManager.createQuery(query).getSingleResult();
    }

    static <ENTITY extends _BaseEntity<?>>
    @PositiveOrZero ENTITY selectRandom(final @Nonnull EntityManager entityManager,
                                        final @Nonnull Class<ENTITY> entityClass) {
        return ThreadLocalRandom.current().nextBoolean()
                ? selectRandomUsingQueryLanguage(entityManager, entityClass)
                : selectRandomUsingCriteriaApi(entityManager, entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private _BaseEntityIT_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
