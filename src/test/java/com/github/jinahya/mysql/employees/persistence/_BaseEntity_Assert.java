package com.github.jinahya.mysql.employees.persistence;

import org.assertj.core.api.AbstractAssert;

import java.io.Serializable;

abstract class _BaseEntity_Assert<
        SELF extends _BaseEntity_Assert<SELF, ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable>
        extends AbstractAssert<SELF, ENTITY> {

    _BaseEntity_Assert(final ENTITY actual, final Class<?> selfType) {
        super(actual, selfType);
    }
}
