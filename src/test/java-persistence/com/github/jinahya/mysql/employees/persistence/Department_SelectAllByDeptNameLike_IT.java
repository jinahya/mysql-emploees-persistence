package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("selectOneByDeptName")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class Department_SelectAllByDeptNameLike_IT
        extends Department__IT {

    private static List<Department> selectAllByDeptNameLike_UsingNamedQuery(final @Nonnull EntityManager entityManager,
                                                                            final @Nonnull String deptNamePattern) {
        return entityManager
                .createNamedQuery("Department.selectAllByDeptNameLike", Department.class)
                .setParameter("deptNamePattern", deptNamePattern)
                .getResultList();
    }

    private static List<Department> selectAllByDeptNameLike_UsingQueryLanguage(final EntityManager entityManager,
                                                                               final String deptNamePattern) {
        return entityManager
                .createQuery(
                        """
                                SELECT e
                                FROM Department AS e
                                WHERE e.deptName LIKE :deptNamePattern""",
                        Department.class
                )
                .setParameter("deptNamePattern", deptNamePattern)
                .getResultList();
    }

    private static List<Department> selectAllByDeptNameLike_CriteriaApi(final EntityManager entityManager,
                                                                        final String deptNamePattern) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Department.class);
        final var root = query.from(Department.class);                  // FROM Department AS e
        query.select(root);                                             // SELECT e
        // @formatter:off
        query.where(                                                    // WHERE e.deptNamePattern LIKE :deptNamePattern
                builder.like(
                        root.get(Department_.deptName),
                        deptNamePattern
                )
        );
        // @formatter:on
        return entityManager
                .createQuery(query)
                .getResultList();
    }

    static List<Department> selectAllByDeptNameLike(final EntityManager entityManager, final String deptNamePattern) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(deptNamePattern, "deptNamePattern is null");
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 1 -> selectAllByDeptNameLike_UsingQueryLanguage(entityManager, deptNamePattern);
            case 0 -> selectAllByDeptNameLike_UsingNamedQuery(entityManager, deptNamePattern);
            default -> selectAllByDeptNameLike_CriteriaApi(entityManager, deptNamePattern);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Stream<String> getDeptNamePatternList() {
        return Stream.of(
                "%vel%",
                "%es"
        );
    }

    @MethodSource({"getDeptNamePatternList"})
    @ParameterizedTest
    void selectAllByDeptNameLike_NamedQuery__(final String deptNamePattern) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllByDeptNameLike_UsingNamedQuery(em, deptNamePattern));
        log.debug("deptNamePattern: {}, names: {}", deptNamePattern,
                  all.stream().map(Department::getDeptName).toList());
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty();
    }

    @MethodSource({"getDeptNamePatternList"})
    @ParameterizedTest
    void selectAllByDeptNameLike_QueryLanguage__(final String deptNamePattern) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllByDeptNameLike_UsingQueryLanguage(em, deptNamePattern));
        log.debug("deptNamePattern: {}, names: {}", deptNamePattern,
                  all.stream().map(Department::getDeptName).toList());
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty();
    }

    @MethodSource({"getDeptNamePatternList"})
    @ParameterizedTest
    void selectAllByDeptNameLike_CriteriaApi__(final String deptNamePattern) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllByDeptNameLike_CriteriaApi(em, deptNamePattern));
        log.debug("deptNamePattern: {}, names: {}", deptNamePattern,
                  all.stream().map(Department::getDeptName).toList());
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @ValueSource(strings = {
            "%부",
            "%과"
    })
    @ParameterizedTest
    void selectAllByDeptNamePattern_Empty_Unknown(final String depNamePattern) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllByDeptNameLike(em, depNamePattern));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isEmpty();
    }
}
