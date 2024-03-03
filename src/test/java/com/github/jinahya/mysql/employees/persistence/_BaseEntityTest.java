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
abstract class _BaseEntityTest<ENTITY extends _BaseEntity<ID>, ID extends Serializable>
        extends __BaseEntityTest<ENTITY, ID> {

    @Deprecated(forRemoval = true)
    _BaseEntityTest(final Class<ENTITY> entityClass, final Class<ID> idClass) {
        super(entityClass, idClass);
    }

    _BaseEntityTest(final Class<ENTITY> entityClass) {
        this(entityClass, null);
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

    // ----------------------------------------------------------------------------------------------------- entityClass
    ENTITY newEntityInstance() {
        try {
            final var constructor = entityClass.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to instantiate " + entityClass, roe);
        }
    }
}
