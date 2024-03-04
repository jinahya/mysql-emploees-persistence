package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.Serializable;

@EnableJpaRepositories(
        basePackageClasses = {
                com.github.jinahya.mysql.employees.persistence.spring.__NoOp.class
        }
)
@SpringBootTest
abstract class _BaseEntityRepositoryIT<
        REPOSITORY extends _BaseEntityRepository<ENTITY, ID>,
        ENTITY extends _BaseEntity<ID>,
        ID extends Serializable>
        extends __BaseEntityRepositoryTest<REPOSITORY, ENTITY, ID> {

    // -----------------------------------------------------------------------------------------------------------------
    _BaseEntityRepositoryIT(final Class<REPOSITORY> repositoryClass) {
        super(repositoryClass);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    @Accessors(fluent = true)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PACKAGE)
    private REPOSITORY repositoryInstance;
}
