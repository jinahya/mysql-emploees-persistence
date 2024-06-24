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
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
final class __JdbcUtils {

    // -----------------------------------------------------------------------------------------------------------------
    static <R> R applyUnwrappedConnection(final @Nonnull EntityManager entityManager,
                                          final @Nonnull Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(function, "function is null");
        return __PersistenceUtils.applyInTransaction(
                entityManager,
                em -> {
                    final var unwrapped = em.unwrap(Connection.class);
                    assert unwrapped != null : "null unwrapped for " + Connection.class;
                    final var uuncloseable = __LangUtils.uncloseable(Connection.class, null, unwrapped);
                    return function.apply(uuncloseable);
                },
                et -> {
                    et.commit();
                    log.debug("committed: {}", et);
                }
        );
    }

    private static <R> R applyUnwrappedConnectionInTransaction(
            final @Nonnull EntityManager entityManager,
            final @Nonnull Function<? super Connection, ? extends R> function,
            final boolean rollback) {
        Objects.requireNonNull(function, "function is null");
        return applyUnwrappedConnection(
                entityManager,
                c -> {
                    try {
                        c.setAutoCommit(false);
                        log.debug("auto-commit disabled");
                        try {
                            final R result = function.apply(c);
                            if (rollback) {
                                c.rollback();
                                log.debug("rolled-back");
                            } else {
                                c.commit();
                                log.debug("commited");
                            }
                            return result;
                        } catch (final Exception e) {
                            c.rollback();
                            log.debug("rolled-back");
                            throw new RuntimeException(e);
                        }
                    } catch (final SQLException sqle) {
                        throw new RuntimeException(sqle);
                    }
                }
        );
    }

    static <R> R applyUnwrappedConnectionInTransaction(final @Nonnull EntityManager entityManager,
                                                       final @Nonnull Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyUnwrappedConnectionInTransaction(entityManager, function, false);
    }

    static <R> R applyUnwrappedConnectionInTransactionAndRollback(
            final @Nonnull EntityManager entityManager,
            final @Nonnull Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyUnwrappedConnectionInTransaction(entityManager, function, true);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private __JdbcUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
