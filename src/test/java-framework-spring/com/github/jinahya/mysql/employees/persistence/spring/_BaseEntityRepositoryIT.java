package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

@SpringBootTest
abstract class _BaseEntityRepositoryIT<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable>
        extends __BaseEntityRepositoryTestBase<REPOSITORY, ENTITY, ID> {

    // -----------------------------------------------------------------------------------------------------------------
    _BaseEntityRepositoryIT(final Class<REPOSITORY> repositoryClass) {
        super(repositoryClass);
    }

    // ---------------------------------------------------------------------------------------------- repositoryInstance

    // --------------------------------------------------------------------------------------------------- entityManager

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PACKAGE)
    private REPOSITORY repositoryInstance;

    @Autowired
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PACKAGE)
    private EntityManager entityManager;
}
