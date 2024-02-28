package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

abstract class BaseEntityTest<ENTITY extends BaseEntity> {

    BaseEntityTest(final Class<ENTITY> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
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

    // -----------------------------------------------------------------------------------------------------------------
    final Class<ENTITY> entityClass;
}
