package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    private static Optional<Department> selectOneByDeptName1(final EntityManager entityManager, final String deptName) {
        try {
            return Optional.of(
                    entityManager
                            .createNamedQuery("Department.selectOneByDeptName", Department.class)
                            .setParameter("deptName", deptName)
                            .getSingleResult() // NoResultException
            );
        } catch (final NoResultException nre) {
            log.error("failed to find a Department by deptName('{})'", deptName, nre);
            return Optional.empty();
        }
    }

    private static Optional<Department> selectOneByDeptName2(final EntityManager entityManager, final String deptName) {
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

    private static Optional<Department> selectOneByDeptName3(final EntityManager entityManager, final String deptName) {
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
            case 1 -> selectOneByDeptName2(entityManager, deptName);
            case 0 -> selectOneByDeptName1(entityManager, deptName);
            default -> selectOneByDeptName3(entityManager, deptName);
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
    void selectOneByDeptName1__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> selectOneByDeptName1(em, deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                    assertBaseEntity(v).hasDeptName(deptName);
                });
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void selectOneByDeptName2__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> selectOneByDeptName2(em, deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                    assertBaseEntity(v).hasDeptName(deptName);
                });
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void selectOneByDeptName3__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> selectOneByDeptName3(em, deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                    assertBaseEntity(v).hasDeptName(deptName);
                });
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectOneByDeptName_Empty_Unknown() {
        // ------------------------------------------------------------------------------------------------------- given
        final var deptName = "Section 8";
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> selectOneByDeptName(em, deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one).isEmpty();
    }
}
