package com.github.jinahya.mysql.employees.persistence;

import org.assertj.core.api.AbstractAssert;

import java.io.Serializable;

public  abstract class _BaseEntityAssert<
        SELF extends _BaseEntityAssert<SELF, ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable>
        extends AbstractAssert<SELF, ENTITY> {

    protected _BaseEntityAssert(final ENTITY actual, final Class<?> selfType) {
        super(actual, selfType);
    }

    public abstract SELF hasId(final ID id);
}
