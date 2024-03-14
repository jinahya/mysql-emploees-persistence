package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
@SuppressWarnings({
        "java:S119" // <ENTITY ...>
})
public interface _BaseEntityRepository<ENTITY extends _BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<ENTITY, ID>,
                QuerydslPredicateExecutor<ENTITY> {

}
