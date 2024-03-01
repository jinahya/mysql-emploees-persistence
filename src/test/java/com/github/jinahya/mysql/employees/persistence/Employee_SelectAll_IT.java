package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Employee_SelectAll_IT extends _BaseEntityIT<Employee, Integer> {

    private static List<Employee> selectAll1(final EntityManager entityManager, final Integer maxResult) {
        return entityManager
                .createNamedQuery("Employee.selectAll", Employee.class)
                .setMaxResults(Optional.ofNullable(maxResult).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Employee> selectAll2(final EntityManager entityManager, final Integer maxResult) {
        return entityManager
                .createQuery("SELECT e FROM Employee AS e", Employee.class)
                .setMaxResults(Optional.ofNullable(maxResult).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Employee> selectAll3(final EntityManager entityManager, final Integer maxResult) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Employee.class);
        final var root = query.from(Employee.class);
        query.select(root);
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResult).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Employee> selectAll(final EntityManager entityManager, final Integer maxResult) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        if (maxResult != null && maxResult < 0) {
            throw new IllegalArgumentException("negative maxResult: " + maxResult);
        }
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAll1(entityManager, maxResult);
            case 1 -> selectAll2(entityManager, maxResult);
            default -> selectAll3(entityManager, maxResult);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    Employee_SelectAll_IT() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAll1__() {
        final var maxResults = ThreadLocalRandom.current().nextInt(8);
        final var all = applyEntityManager(em -> selectAll1(em, maxResults));
        if (maxResults > 0) { // https://bugs.eclipse.org/bugs/show_bug.cgi?id=328730
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAll2__() {
        final var maxResults = ThreadLocalRandom.current().nextInt(8);
        final var all = applyEntityManager(em -> selectAll2(em, maxResults));
        if (maxResults > 0) { // https://bugs.eclipse.org/bugs/show_bug.cgi?id=328730
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAll3__() {
        final var maxResults = ThreadLocalRandom.current().nextInt(8);
        final var all = applyEntityManager(em -> selectAll3(em, maxResults));
        if (maxResults > 0) { // https://bugs.eclipse.org/bugs/show_bug.cgi?id=328730
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
