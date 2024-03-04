package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Employee_SelectMinMaxBirthDate_IT extends _BaseEntityIT<Employee, Integer> {

    private static Object[] selectMinMaxBirthDate1(final EntityManager entityManager) {
        return entityManager
                .createNamedQuery("Employee.selectMinMaxBirthDate", Object[].class)
                .getSingleResult();
    }

    private static Object[] selectMinMaxBirthDate2(final EntityManager entityManager) {
        return entityManager
                .createQuery("SELECT MIN(e.birthDate), MAX(e.birthDate) FROM Employee AS e", Object[].class)
                .getSingleResult();
    }

    private static Object[] selectMinMaxBirthDate3(final EntityManager entityManager) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Object[].class);
        final var root = query.from(Employee.class);            // FROM Employee AS e
        query.select(builder.array(                             // SELECT
                builder.least(root.get(Employee_.birthDate)),   // MIN(e.birthDate),
                builder.greatest(root.get(Employee_.birthDate)) // MAX(e.birthDate)
        ));
        return entityManager.createQuery(query).getSingleResult();
    }

    static Object[] selectMinMaxBirthDate(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectMinMaxBirthDate1(entityManager);
            case 1 -> selectMinMaxBirthDate2(entityManager);
            default -> selectMinMaxBirthDate3(entityManager);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    Employee_SelectMinMaxBirthDate_IT() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectMinMaxBirthDate1__() {
        final var tuple = applyEntityManager(
                Employee_SelectMinMaxBirthDate_IT::selectMinMaxBirthDate1
        );
        assertThat(tuple).isNotNull().hasSize(2);
        log.debug("MIN(birthDate): {}", tuple[0]);
        log.debug("MAX(birthDate): {}", tuple[1]);
    }

    @Test
    void selectMinMaxBirthDate2__() {
        final var tuple = applyEntityManager(
                Employee_SelectMinMaxBirthDate_IT::selectMinMaxBirthDate2
        );
        assertThat(tuple).isNotNull().hasSize(2);
        log.debug("MIN(birthDate): {}", tuple[0]);
        log.debug("MAX(birthDate): {}", tuple[1]);
    }

    @Test
    void selectMinMaxBirthDate3__() {
        final var tuple = applyEntityManager(
                Employee_SelectMinMaxBirthDate_IT::selectMinMaxBirthDate3
        );
        assertThat(tuple).isNotNull().hasSize(2);
        log.debug("MIN(birthDate): {}", tuple[0]);
        log.debug("MAX(birthDate): {}", tuple[1]);
    }
}
