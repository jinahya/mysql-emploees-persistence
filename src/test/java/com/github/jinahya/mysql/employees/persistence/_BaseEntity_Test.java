package com.github.jinahya.mysql.employees.persistence;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
abstract class _BaseEntity_Test<ENTITY extends _BaseEntity<ID>, ID extends Serializable>
        extends __BaseEntity__Test<ENTITY, ID> {

    /**
     * Creates a new instance for testing specified entity class.
     *
     * @param entityClass the entity class to test
     * @see #entityClass
     */
    _BaseEntity_Test(final Class<ENTITY> entityClass) {
        super(entityClass);
    }
}
