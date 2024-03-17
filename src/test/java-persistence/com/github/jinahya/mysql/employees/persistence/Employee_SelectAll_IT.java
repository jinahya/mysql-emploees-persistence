package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Employee_SelectAll_IT
        extends _BaseEntityIT<Employee, Integer> {

    private static List<Employee> selectAll1(final EntityManager entityManager, final @Nullable Integer maxResult) {
        return entityManager
                .createNamedQuery("Employee.selectAll", Employee.class)
                .setMaxResults(Optional.ofNullable(maxResult).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Employee> selectAll2(final EntityManager entityManager, final @Nullable Integer maxResult) {
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM Employee AS e
                                ORDER BY e.empNo ASC""",
                        Employee.class
                )
                .setMaxResults(Optional.ofNullable(maxResult).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Employee> selectAll3(final EntityManager entityManager, final @Nullable Integer maxResult) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Employee.class);
        final var employee = query.from(Employee.class);                                           // FROM Employee AS e
        query.select(employee);                                                                    // SELECT e
        query.orderBy(builder.asc(employee.get(Employee_.empNo)));                                 // ORDER BY e.empNo
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
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8);
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll1(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Employee::getEmpNo));
        if (maxResults != null) {
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAll2__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8);
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll2(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Employee::getEmpNo));
        if (maxResults != null) {
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAll3__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8);
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll3(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Employee::getEmpNo));
        if (maxResults != null) {
            assertThat(all).hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
