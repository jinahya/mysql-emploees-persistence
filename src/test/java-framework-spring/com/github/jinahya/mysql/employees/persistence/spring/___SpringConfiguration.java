package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(
        basePackageClasses = {
                _BaseEntity.class
        }
)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = {
                _BaseEntityRepository.class
        }
)
@EnableAutoConfiguration
@SpringBootConfiguration
class ___SpringConfiguration {

}
