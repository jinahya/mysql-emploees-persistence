package com.github.jinahya.mysql.employees.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class _PersistenceUnitIT
        extends __PersistenceIT {

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void logIdAttributes__() {
        applyEntityManager(em -> {
            final var metamodel = em.getMetamodel();
            metamodel.getEntities().forEach(et -> {
                log.debug("entityType: {}", et);
                final var persistenceType = et.getPersistenceType();
                log.debug("\tpersistenceType: {}", persistenceType);
                final var javaType = et.getJavaType();
                log.debug("\tjavaType: {}", javaType);
                final var managedType = metamodel.managedType(javaType);
                log.debug("\tmanagedType: {}", managedType);
                managedType.getAttributes().forEach(a -> {
                    log.debug("\t\tattribute: {}", a);
                });
            });
            return null;
        });
    }
}
