package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import org.mockito.Mockito;

import java.io.Serializable;

abstract class _BaseEntityRepositoryTest<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable>
        extends __BaseEntityRepositoryTest<REPOSITORY, ENTITY, ID> {

    _BaseEntityRepositoryTest(final Class<REPOSITORY> repositoryClass) {
        super(repositoryClass);
    }

    // ------------------------------------------------------------------------------------------------- repositoryClass
    REPOSITORY newRepositoryMock() {
        return Mockito.mock(repositoryClass);
    }
}
