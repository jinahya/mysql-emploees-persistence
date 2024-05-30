package com.github.jinahya.mysql.employees.persistence;

import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class __PersistenceProducer {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the persistence unit. The value is {@value}.
     */
    private static final String PERSISTENCE_UNIT_NAME = "employeesPU";

    /**
     * The entity manager factory for the {@value #PERSISTENCE_UNIT_NAME}.
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    // -----------------------------------------------------------------------------------------------------------------
    @Produces
    EntityManager produceEntityManager() {
        final var entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        log.debug("producing entityManager: {}", entityManager);
        return entityManager;
    }

    void disposeEntityManager(final @Disposes EntityManager entityManager) {
        log.debug("disposing entityManager: {}", entityManager);
        entityManager.close();
    }
}
