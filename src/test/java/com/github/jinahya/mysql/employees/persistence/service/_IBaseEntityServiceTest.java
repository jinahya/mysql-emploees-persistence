package com.github.jinahya.mysql.employees.persistence.service;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;

import java.io.Serializable;

abstract class _IBaseEntityServiceTest<
        SERVICE extends _IBaseEntityService<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable>
        extends __IBaseEntityServiceTest<SERVICE, ENTITY, ID> {

    _IBaseEntityServiceTest(final Class<SERVICE> serviceClass, final Class<ENTITY> entityClass) {
        super(serviceClass, entityClass);
    }
}
