package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("selectAll")
class Department_SelectAll_IT extends Department__IT {

    private static List<Department> selectAll1(final EntityManager entityManager, final @Nullable Integer maxResults) {
        return entityManager
                .createNamedQuery("Department.selectAll", Department.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Department> selectAll2(final EntityManager entityManager, final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM Department AS e
                                ORDER BY e.deptNo ASC""",
                        Department.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Department> selectAll3(final EntityManager entityManager, final @Nullable Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Department.class);
        final var root = query.from(Department.class);                                          // FROM Department AS e
        query.select(root);                                                                     // SELECT e
        query.orderBy(builder.asc(root.get(Department_.deptNo)));                               // ORDER BY e.deptNo ASC
        return entityManager.createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Department> selectAll(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAll1(entityManager, maxResults);
            case 1 -> selectAll2(entityManager, maxResults);
            default -> selectAll3(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAll1__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll1(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Department::getDeptNo));
        if (maxResults != null) {
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAll2__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll2(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Department::getDeptNo));
        if (maxResults != null) {
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAll3__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll3(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(Department::getDeptNo));
        if (maxResults != null) {
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
