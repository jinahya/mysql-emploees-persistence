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

abstract class _BaseId_Test<ID extends _BaseId> {

    // -----------------------------------------------------------------------------------------------------------------
    _BaseId_Test(final Class<ID> idClass) {
        super();
        this.idClass = Objects.requireNonNull(idClass, "idClass is null");
    }

    // --------------------------------------------------------------------------------------------------- getter/setter
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

    // -------------------------------------------------------------------------------------------------------- toString
    @DisplayName("toString()")
    @Test
    void toString__() {
        final var instance = newIdInstance();
        assertThatCode(() -> {
            final var string = instance.toString();
            assertThat(string).isNotBlank();
        }).doesNotThrowAnyException();
    }

    // ------------------------------------------------------------------------------------------------- equals/hashCode
    @Test
    void equals__() {
        final var verifierApi = EqualsVerifier.forClass(idClass);
        equals__(verifierApi);
        verifierApi.verify();
    }

    SingleTypeEqualsVerifierApi<ID> equals__(final SingleTypeEqualsVerifierApi<ID> verifierApi) {
        return verifierApi;
    }

    // --------------------------------------------------------------------------------------------------------- idClass
    ID newIdInstance() {
        try {
            final var constructor = idClass.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to instantiate " + idClass, roe);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<ID> idClass;
}
