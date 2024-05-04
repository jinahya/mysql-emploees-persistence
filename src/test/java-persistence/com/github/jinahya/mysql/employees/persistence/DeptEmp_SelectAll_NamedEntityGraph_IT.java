package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("selectAll_NamedEntityGraph")
@Slf4j
class DeptEmp_SelectAll_NamedEntityGraph_IT
        extends DeptEmp__IT {

    // -----------------------------------------------------------------------------------------------------------------
    private static
    @Nonnull List<DeptEmp> selectAllUsingNamedQuery(final @Nonnull EntityManager entityManager,
                                                    final @Nullable Integer maxResults) {
        final var entityGraph = entityManager.createEntityGraph("DeptEmp.employeeAndDepartment");
        return entityManager
                .createNamedQuery("DeptEmp.selectAll", DeptEmp.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
//                .setHint("jakarta.persistence.loadgraph", entityGraph)
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                       final @Nullable Integer maxResults) {
        final var entityGraph = entityManager.createEntityGraph("DeptEmp.employeeAndDepartment");
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM DeptEmp AS e
                                ORDER BY e.empNo ASC, e.deptNo ASC""",
                        DeptEmp.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
//                .setHint("jakarta.persistence.loadgraph", entityGraph)
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                     final @Nullable Integer maxResults) {
        final var entityGraph = entityManager.createEntityGraph("DeptEmp.employeeAndDepartment");
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
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
//                .setHint("jakarta.persistence.loadgraph", entityGraph)
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
    private void verify(final int maxResults, List<DeptEmp> all) {
        assertThat(all)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(DeptEmp_SelectAll__IT.COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO)
                .hasSizeLessThanOrEqualTo(maxResults)
                .allSatisfy(e -> {
                    log.debug("department: {}", e.getDepartment());
                    log.debug("employee: {}", e.getEmployee());
                });
    }

    @Test
    void selectAllUsingNamedQuery__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = 4;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingNamedQuery(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, all);
    }

    @Test
    void selectAllUsingQueryLanguage__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = 4;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingQueryLanguage(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, all);
    }

    @Test
    void selectAllUsingCriteriaApi__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = 4;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllUsingCriteriaApi(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, all);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAll__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = 4;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAll(em, maxResults));
        // -------------------------------------------------------------------------------------------------------- then
        verify(maxResults, all);
    }
}
