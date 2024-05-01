package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
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

    private static final Comparator<DeptEmp> COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO
            = Comparator.comparing(DeptEmp::getEmpNo).thenComparing(DeptEmp::getDeptNo);

    // -----------------------------------------------------------------------------------------------------------------
    private static
    @Nonnull List<DeptEmp> selectAllUsingNamedQuery(final @Nonnull EntityManager entityManager,
                                                    final @Nullable Integer maxResults) {
        return entityManager
                .createNamedQuery("DeptEmp.selectAll", DeptEmp.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                       final @Nullable Integer maxResults) {
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

    private static
    @Nonnull List<DeptEmp> selectAllUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                     final @Nullable Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptEmp.class);
        // @formatter:off
        final var root = query.from(DeptEmp.class);                                                 // FROM DeptEmp AS e
        query.select(root);                                                                         // SELECT e
        query.orderBy(                                                                              // ORDER BY
                builder.asc(root.get(DeptEmp_.empNo)),                                              //     e.empNo ASC
                builder.asc(root.get(DeptEmp_.deptNo))                                              //     e.deptNo ASC
        );
        // @formatter:on
        return entityManager.createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<DeptEmp> selectAll(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllUsingNamedQuery(entityManager, maxResults);
            case 1 -> selectAllUsingQueryLanguage(entityManager, maxResults);
            default -> selectAllUsingCriteriaApi(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAllUsingNamedQuery__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingNamedQuery(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO);
        if (maxResults != null) {
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAllUsingQueryLanguage__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingQueryLanguage(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO);
        if (maxResults != null) {
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    @Test
    void selectAllUsingCriteriaApi__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingCriteriaApi(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO);
        if (maxResults != null) {
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }
}
