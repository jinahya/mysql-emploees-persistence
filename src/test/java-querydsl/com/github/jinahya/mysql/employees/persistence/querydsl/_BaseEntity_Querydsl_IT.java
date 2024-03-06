package com.github.jinahya.mysql.employees.persistence.querydsl;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import com.github.jinahya.mysql.employees.persistence._BaseEntityTestUtils;
import com.github.jinahya.mysql.employees.persistence._PersistenceProducer;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.WeldJunit5AutoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.Serializable;
import java.util.Objects;

@AddBeanClasses({
        _PersistenceProducer.class
})
@ExtendWith(WeldJunit5AutoExtension.class)
abstract class _BaseEntity_Querydsl_IT<ENTITY extends _BaseEntity<ID>, ID extends Serializable> {

    _BaseEntity_Querydsl_IT(final Class<ENTITY> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void doNothing() {
        // empty
    }

    // ----------------------------------------------------------------------------------------------------- entityClass
    Class<ID> idClass() {
        return _BaseEntityTestUtils.idClass(entityClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<ENTITY> entityClass;
}
