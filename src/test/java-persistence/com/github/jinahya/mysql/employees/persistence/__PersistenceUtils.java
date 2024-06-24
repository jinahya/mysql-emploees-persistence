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
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
final class __PersistenceUtils {

    // -----------------------------------------------------------------------------------------------------------------
    static <ENTITY extends _BaseEntity<?>> Set<Attribute<? super ENTITY, ?>> getIdAttributes(
            final ManagedType<ENTITY> managedType) {
        Objects.requireNonNull(managedType, "managedType is null");
        return managedType.getAttributes().stream()
                .filter(a -> a instanceof SingularAttribute<?, ?>)
                .filter(a -> ((SingularAttribute<?, ?>) a).isId())
                .collect(Collectors.toSet());
    }

    static <ENTITY extends _BaseEntity<?>> void acceptEachIdAttribute(
            final ManagedType<ENTITY> managedType, final Consumer<? super Attribute<? super ENTITY, ?>> consumer) {
        Objects.requireNonNull(managedType, "managedType is null");
        Objects.requireNonNull(consumer, "consumer is null");
        getIdAttributes(managedType).forEach(consumer);
    }

    // -----------------------------------------------------------------------------------------------------------------
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
    private __PersistenceUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
