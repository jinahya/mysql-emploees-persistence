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
class DeptEmp_SelectAll_IT
        extends DeptEmp__IT {

    private static List<DeptEmp> selectAll_NQ(final EntityManager entityManager, final @Nullable Integer maxResults) {
        return entityManager
                .createNamedQuery("DeptEmp.selectAll", DeptEmp.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<DeptEmp> selectAll_QL(final EntityManager entityManager, final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM DeptEmp AS e
                                ORDER BY e.empNo ASC, e.deptNo ASC""",
                        DeptEmp.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<DeptEmp> selectAll_CA(final EntityManager entityManager, final @Nullable Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptEmp.class);
        final var root = query.from(DeptEmp.class);                                             // FROM DeptEmp AS e
        query.select(root);                                                                     // SELECT e
        query.orderBy(                                                                          // ORDER BY
                builder.asc(root.get(DeptEmp_.empNo)),                                          //     e.empNo ASC
                builder.asc(root.get(DeptEmp_.deptNo))                                          //     e.deptNo ASC
        );
        return entityManager.createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<DeptEmp> selectAll(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAll_NQ(entityManager, maxResults);
            case 1 -> selectAll_QL(entityManager, maxResults);
            default -> selectAll_CA(entityManager, maxResults);
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
        final var all = applyEntityManager(em -> selectAll_NQ(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(DeptEmp::getDeptNo));
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
        final var all = applyEntityManager(em -> selectAll_QL(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(DeptEmp::getDeptNo));
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
        final var all = applyEntityManager(em -> selectAll_CA(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(DeptEmp::getDeptNo));
        if (maxResults != null) {
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
