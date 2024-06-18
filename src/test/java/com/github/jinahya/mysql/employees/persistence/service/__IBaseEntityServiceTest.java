package com.github.jinahya.mysql.employees.persistence.service;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import com.github.jinahya.mysql.employees.persistence._BaseEntityTestUtils;

import java.io.Serializable;
import java.util.Objects;

abstract class __IBaseEntityServiceTest<
        SERVICE extends _IBaseEntityService<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable> {

    __IBaseEntityServiceTest(final Class<SERVICE> serviceClass, final Class<ENTITY> entityClass) {
        super();
        this.serviceClass = Objects.requireNonNull(serviceClass, "serviceClass is null");
        this.entityClass = entityClass;
        idClass = _BaseEntityTestUtils.idClass(entityClass);
    }

    final Class<SERVICE> serviceClass;

    final Class<ENTITY> entityClass;

    final Class<ID> idClass;
}
