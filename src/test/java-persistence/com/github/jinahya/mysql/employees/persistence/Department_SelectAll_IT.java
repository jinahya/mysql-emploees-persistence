package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class Department_SelectAll_IT extends _BaseEntityIT<Department, String> {

    private static List<Department> selectAll1(final EntityManager entityManager) {
        return entityManager
                .createNamedQuery("Department.selectAll", Department.class)
                .getResultList();
    }

    private static List<Department> selectAll2(final EntityManager entityManager) {
        return entityManager
                .createQuery("SELECT e FROM Department AS e", Department.class)
                .getResultList();
    }

    private static List<Department> selectAll3(final EntityManager entityManager) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Department.class);
        final var root = query.from(Department.class); // FROM Department AS e
        query.select(root);                            // SELECT e
        return entityManager.createQuery(query).getResultList();
    }

    static List<Department> selectAll(final EntityManager entityManager) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAll1(entityManager);
            case 1 -> selectAll2(entityManager);
            default -> selectAll3(entityManager);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    Department_SelectAll_IT() {
        super(Department.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAll1__() {
        final var all = applyEntityManager(Department_SelectAll_IT::selectAll1);
        assertThat(all).isNotNull().doesNotContainNull().allSatisfy(d -> {
        });
    }

    @Test
    void selectAll2__() {
        final var all = applyEntityManager(Department_SelectAll_IT::selectAll2);
        assertThat(all).isNotNull().doesNotContainNull().allSatisfy(d -> {
        });
    }

    @Test
    void selectAll3__() {
        final var all = applyEntityManager(Department_SelectAll_IT::selectAll3);
        assertThat(all).isNotNull().doesNotContainNull().allSatisfy(d -> {
        });
    }
}
