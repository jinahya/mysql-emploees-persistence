package com.github.jinahya.mysql.employees.persistence;

import lombok.extern.slf4j.Slf4j;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@Slf4j
abstract class _BaseEntity_Basic_Test<ENTITY extends _BaseEntity<ID>, ID extends Serializable>
        extends __BaseEntity__Test<ENTITY, ID> {

    /**
     * Creates a new instance for testing specified entity class.
     *
     * @param entityClass the entity class to test
     * @see #entityClass
     */
    _BaseEntity_Basic_Test(final Class<ENTITY> entityClass) {
        super(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("idClass()")
    @Test
    void idClass_() {
        final var idClass = idClass();
        assertThat(idClass)
                .as("resolved <ID> class of %s", entityClass)
                .isNotNull();
    }

    // -------------------------------------------------------------------------------------------------------- toString
    @DisplayName("toString()")
    @Test
    void toString__() {
        final var instance = newEntityInstance();
        assertThatCode(() -> {
            final var string = instance.toString();
            assertThat(string).isNotBlank();
        }).doesNotThrowAnyException();
    }

    // ------------------------------------------------------------------------------------------------- equals/hashCode
    @Test
    void equals__() {
        final var verifierApi = EqualsVerifier.forClass(entityClass);
        equals__(verifierApi);
        verifierApi.verify();
    }

    SingleTypeEqualsVerifierApi<ENTITY> equals__(final SingleTypeEqualsVerifierApi<ENTITY> verifierApi) {
        return verifierApi;
    }

    // --------------------------------------------------------------------------------------------------- getter/setter
    @Test
    void accessors__() {
        final var instance = newEntityInstance();
        try {
            final var info = Introspector.getBeanInfo(entityClass);
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
            throw new RuntimeException("failed to check accessors of " + entityClass, roe);
        } catch (final IntrospectionException ie) {
            throw new RuntimeException("failed to introspect " + entityClass, ie);
        }
    }
}
