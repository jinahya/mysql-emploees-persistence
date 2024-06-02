package com.github.jinahya.mysql.employees.persistence;

import com.github.jinahya.mysql.employees.persistence.service._BaseEntityPersistenceService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.Objects;

abstract class BaseEntityServiceImpl<
        SERVICE extends _BaseEntityPersistenceService<ENTITY>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable> {

    BaseEntityServiceImpl(final Class<SERVICE> serviceClass) {
        super();
        this.serviceClass = Objects.requireNonNull(serviceClass, "serviceClass is null");
    }

    // ---------------------------------------------------------------------------------------------------- serviceClass

    // --------------------------------------------------------------------------------------------------- entityManager

    // -----------------------------------------------------------------------------------------------------------------
    final Class<SERVICE> serviceClass;

    @Inject
    private EntityManager entityManager;
}
