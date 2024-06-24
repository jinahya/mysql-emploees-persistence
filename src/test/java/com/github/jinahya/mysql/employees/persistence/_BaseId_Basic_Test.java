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

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

abstract class _BaseId_Basic_Test<ID extends _BaseId>
        extends __BaseId_Test<ID> {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance for testing specified id class.
     *
     * @param idClass the id class to test.
     */
    _BaseId_Basic_Test(final Class<ID> idClass) {
        super(idClass);
    }

    // ------------------------------------------------------------------------------------------------- getters/setters
    @DisplayName("setXxx(getXxx())")
    @Test
    void accessors__() {
        final var instance = newIdInstance();
        try {
            final var info = Introspector.getBeanInfo(idClass);
            for (final var descriptor : info.getPropertyDescriptors()) {
                final var reader = descriptor.getReadMethod();
                if (reader == null) {
                    continue;
                }
                if (!reader.canAccess(instance)) {
                    reader.setAccessible(true);
                }
                final var value = reader.invoke(instance);
                final var writer = descriptor.getWriteMethod();
                if (writer == null) {
                    continue;
                }
                if (!writer.canAccess(instance)) {
                    writer.setAccessible(true);
                }
                assertThatCode(() -> {
                    writer.invoke(instance, value);
                }).doesNotThrowAnyException();
            }
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to check accessors of " + idClass, roe);
        } catch (final IntrospectionException ie) {
            throw new RuntimeException("failed to introspect " + idClass, ie);
        }
    }

    // ------------------------------------------------------------------------------------------------ toString()String
    @DisplayName("toString()String")
    @Test
    void toString__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var instance = newIdInstance();
        // -------------------------------------------------------------------------------------------------------- when
        final var string = instance.toString();
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(string).isNotBlank();
    }

    // ------------------------------------------------------------------------------------------------- equals/hashCode
    @DisplayName("equals(Object)boolean")
    @Test
    void equals__() {
        if (Boolean.parseBoolean(System.getProperty("eclipselink.woven"))) {
            return;
        }
        final var verifierApi = EqualsVerifier.forClass(idClass);
        equals__(verifierApi);
        verifierApi.verify();
    }

    SingleTypeEqualsVerifierApi<ID> equals__(final SingleTypeEqualsVerifierApi<ID> verifierApi) {
        return verifierApi;
    }
}
