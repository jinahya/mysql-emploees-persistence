package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Department_SelectOneByDeptName_IT extends _BaseEntityIT<Department, String> {

    private static Department selectOneByDeptName1(final EntityManager entityManager, final String deptName) {
        return entityManager
                .createNamedQuery("Department.selectOneByDeptName", Department.class)
                .setParameter("deptName", deptName)
                .getSingleResult();
    }

    private static Department selectOneByDeptName2(final EntityManager entityManager, final String deptName) {
        return entityManager
                .createQuery("SELECT e FROM Department AS e WHERE e.deptName = :deptName", Department.class)
                .setParameter("deptName", deptName)
                .getSingleResult();
    }

    private static Department selectOneByDeptName3(final EntityManager entityManager, final String deptName) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Department.class);
        final var root = query.from(Department.class);                        // FROM Department AS e
        query.select(root);                                                   // SELECT e
        query.where(builder.equal(root.get(Department_.deptName), deptName)); // WHERE e.deptName = :deptName
        return entityManager.createQuery(query).getSingleResult();
    }

    static Department selectOneByDeptName(final EntityManager entityManager, final String deptName) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 1 -> selectOneByDeptName2(entityManager, deptName);
            case 0 -> selectOneByDeptName1(entityManager, deptName);
            default -> selectOneByDeptName3(entityManager, deptName);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    Department_SelectOneByDeptName_IT() {
        super(Department.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Stream<String> getDeptNameStream() {
        return applyEntityManager(
                em -> Department_SelectAll_IT.selectAll(em)
                        .stream()
                        .map(Department::getDeptName)
        );
    }

    @MethodSource({"getDeptNameStream"})
    @ParameterizedTest
    void selectOneByDeptName1__(final String deptName) {
        applyEntityManager(em -> {
            final var one = em.createNamedQuery("Department.selectOneByDeptName", Department.class)
                    .setParameter("deptName", deptName)
                    .getSingleResult();
            assertThat(one).isNotNull().satisfies(d -> {
                assertThat(d.getDeptName()).isEqualTo(deptName);
            });
            return null;
        });
    }

    @MethodSource({"getDeptNameStream"})
    @ParameterizedTest
    void selectOneByDeptName2__(final String deptName) {
        applyEntityManager(em -> {
            final var one = selectOneByDeptName2(em, deptName);
            assertThat(one).isNotNull().satisfies(d -> {
                assertThat(d.getDeptName()).isEqualTo(deptName);
            });
            return null;
        });
    }

    @MethodSource({"getDeptNameStream"})
    @ParameterizedTest
    void selectOneByDeptName3__(final String deptName) {
        applyEntityManager(em -> {
            final var one = selectOneByDeptName3(em, deptName);
            assertThat(one).isNotNull().satisfies(d -> {
                assertThat(d.getDeptName()).isEqualTo(deptName);
            });
            return null;
        });
    }
}
