package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jinahya.mysql.employees.persistence._BaseEntity_Assertions.assertBaseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("selectOneByDeptName")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class Department_SelectOneByDeptName_IT
        extends Department__IT {

    private static Optional<Department> selectOneByDeptNameUsingNamedQuery(final EntityManager entityManager,
                                                                           final String deptName) {
        try {
            return Optional.of(
                    entityManager
                            .createNamedQuery("Department.selectOneByDeptName", Department.class)
                            .setParameter("deptName", deptName)
                            .getSingleResult() // NoResultException
            );
        } catch (final NoResultException nre) {
            log.error("no result for deptName('{})'", deptName, nre);
            return Optional.empty();
        }
    }

    private static Optional<Department> selectOneByDeptNameQueryLanguage(final EntityManager entityManager,
                                                                         final String deptName) {
        try {
            return Optional.of(
                    entityManager
                            .createQuery(
                                    """
                                            SELECT e
                                            FROM Department AS e
                                            WHERE e.deptName = :deptName""",
                                    Department.class
                            )
                            .setParameter("deptName", deptName)
                            .getSingleResult() // NoResultException
            );
        } catch (final NoResultException nre) {
            log.error("failed to find a Department by deptName('{})'", deptName, nre);
            return Optional.empty();
        }
    }

    private static Optional<Department> selectOneByDeptNameCriteriaApi(final EntityManager entityManager,
                                                                       final String deptName) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Department.class);
        final var root = query.from(Department.class);                                   // FROM Department AS e
        query.select(root);                                                              // SELECT e
        query.where(builder.equal(root.get(Department_.deptName), deptName));            // WHERE e.deptName = :deptName
        try {
            return Optional.of(
                    entityManager
                            .createQuery(query)
                            .getSingleResult() // NoResultException
            );
        } catch (final NoResultException nre) {
            log.error("failed to find a Department by deptName('{})'", deptName, nre);
            return Optional.empty();
        }
    }

    static Optional<Department> selectOneByDeptName(final EntityManager entityManager, final String deptName) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(deptName, "deptName is null");
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 1 -> selectOneByDeptNameQueryLanguage(entityManager, deptName);
            case 0 -> selectOneByDeptNameUsingNamedQuery(entityManager, deptName);
            default -> selectOneByDeptNameCriteriaApi(entityManager, deptName);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<String> getDeptNameList() {
        return applyEntityManager(
                em -> em.createQuery("SELECT e.deptName FROM Department AS e ORDER BY e.deptNo ASC", String.class)
                        .setMaxResults(32)
                        .getResultList());
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void selectOneByDeptNameUsingNamedQuery__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> selectOneByDeptNameUsingNamedQuery(em, deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                    assertBaseEntity(v).hasDeptName(deptName);
                });
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void selectOneByDeptNameUsingQueryLanguage__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> selectOneByDeptNameQueryLanguage(em, deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                    assertBaseEntity(v).hasDeptName(deptName);
                });
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void selectOneByDeptNameUsingCriteriaApi__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> selectOneByDeptNameCriteriaApi(em, deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                    assertBaseEntity(v).hasDeptName(deptName);
                });
    }

    // -----------------------------------------------------------------------------------------------------------------
    @ValueSource(strings = {
            "Section 8",
            "인사과"
    })
    @ParameterizedTest
    void selectOneByDeptName_Empty_Unknown(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> selectOneByDeptName(em, deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isEmpty();
    }
}
