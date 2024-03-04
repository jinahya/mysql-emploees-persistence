package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.util.Objects;

abstract class __BaseEntityRepositoryTest<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable> {

    // -----------------------------------------------------------------------------------------------------------------
    __BaseEntityRepositoryTest(final Class<REPOSITORY> repositoryClass) {
        super();
        this.repositoryClass = Objects.requireNonNull(repositoryClass, "repositoryClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void doNothing__() {
    }

    // ------------------------------------------------------------------------------------------------- repositoryClass
    @SuppressWarnings({"unchecked"})
    Class<ENTITY> entityClass() {
        return (Class<ENTITY>) Objects.requireNonNull(
                ResolvableType
                        .forClass(repositoryClass)
                        .as(_BaseEntityRepository.class)
                        .getGeneric(0)
                        .resolve()
        );
    }

    @SuppressWarnings({"unchecked"})
    Class<ID> idClass() {
        return (Class<ID>) Objects.requireNonNull(
                ResolvableType
                        .forClass(repositoryClass)
                        .as(_BaseEntityRepository.class)
                        .getGeneric(2)
                        .resolve()
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<REPOSITORY> repositoryClass;
}
