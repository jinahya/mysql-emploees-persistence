package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * A base repository interface for subclasses of {@link _BaseEntity} class.
 *
 * @param <ENTITY> the type the entity
 * @param <ID>     the type of {@code ID} of the {@link ENTITY} type
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@NoRepositoryBean
@SuppressWarnings({
        "java:S114", // interface _Base...
        "java:S119"  // <ENTITY ...>
})
public interface _BaseEntityRepository<ENTITY extends _BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<ENTITY, ID>,
                JpaSpecificationExecutor<ENTITY> {

}
