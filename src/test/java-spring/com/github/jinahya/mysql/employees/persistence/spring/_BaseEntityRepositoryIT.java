package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;

@SpringBootTest
abstract class _BaseEntityRepositoryIT<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable>
        extends __BaseEntityRepositoryTest<REPOSITORY, ENTITY, ID> {

    _BaseEntityRepositoryIT(final Class<REPOSITORY> repositoryClass) {
        super(repositoryClass);
    }
}
