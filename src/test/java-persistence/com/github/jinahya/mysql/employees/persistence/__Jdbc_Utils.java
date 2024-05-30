package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
final class __Jdbc_Utils {

    // -----------------------------------------------------------------------------------------------------------------
    static <R> R applyUnwrappedConnection(final @Nonnull EntityManager entityManager,
                                          final @Nonnull Function<? super Connection, ? extends R> function) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(function, "function is null");
        return __Persistence_Utils.applyInTransaction(
                entityManager,
                em -> {
                    final var unwrapped = em.unwrap(Connection.class);
                    assert unwrapped != null : "null unwrapped for " + Connection.class;
                    final var uuncloseable = __Lang_Utils.uncloseable(Connection.class, null, unwrapped);
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
    private __Jdbc_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
