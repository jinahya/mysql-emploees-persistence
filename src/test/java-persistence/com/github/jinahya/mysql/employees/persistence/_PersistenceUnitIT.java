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
