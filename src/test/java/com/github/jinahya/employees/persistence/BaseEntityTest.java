package com.github.jinahya.employees.persistence;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.api.Test;

import java.util.Objects;

abstract class BaseEntityTest<ENTITY extends BaseEntity> {

    BaseEntityTest(final Class<ENTITY> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void equals__() {
        final var verifierApi = EqualsVerifier.forClass(entityClass);
        equals__(verifierApi);
        verifierApi.verify();
    }

    void equals__(final SingleTypeEqualsVerifierApi<ENTITY> verifierApi) {
        // empty
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<ENTITY> entityClass;
}