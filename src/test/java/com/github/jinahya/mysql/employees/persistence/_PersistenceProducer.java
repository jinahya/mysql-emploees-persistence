package com.github.jinahya.mysql.employees.persistence;

import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class _PersistenceProducer {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the persistence unit. The value is {@value}.
     */
    private static final String PERSISTENCE_UNIT_NAME = "employeesPU";

    /**
     * The entity manager factory.
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Applies an entity manager to specified function and returns the result.
     *
     * @param function the function to be applied with an entity manager.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     */
    static <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        final var entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return Objects.requireNonNull(function, "function is null").apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Applies an entity manager, along with a second argument provided by specified supplier, to specified function and
     * returns the result.
     *
     * @param function the function to be applied.
     * @param supplier the supplier for the second argument.
     * @param <U>      second argument type parameter
     * @param <R>      result type parameter
     * @return the result of the function.
     */
    static <U, R> R applyEntityManager(final BiFunction<? super EntityManager, ? super U, ? extends R> function,
                                       final Supplier<? extends U> supplier) {
        return applyEntityManager(e -> function.apply(e, supplier.get()));
    }

    // -----------------------------------------------------------------------------------------------------------------
    public @Produces EntityManager produceEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public void disposeEntityManager(final @Disposes EntityManager entityManager) {
        entityManager.close();
    }
}
