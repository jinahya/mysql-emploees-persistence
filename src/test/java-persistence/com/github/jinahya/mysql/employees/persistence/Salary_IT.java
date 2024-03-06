package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class Salary_IT extends _BaseEntityIT<Salary, SalaryId> {

    private static List<Salary> selectAllByEmpNo1(final EntityManager entityManager, final int empNo,
                                                  final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createNamedQuery("Salary.selectAllByEmpNo", Salary.class)
                .setParameter("empNo", empNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectAllByEmpNo2(final EntityManager entityManager, final int empNo,
                                                  final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createQuery("""
                                SELECT e
                                FROM Salary AS e
                                WHERE e.empNo = :empNo
                                ORDER BY e.fromDate DESC""",
                        Salary.class)
                .setParameter("empNo", empNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectAllByEmpNo3(final EntityManager entityManager, final int empNo,
                                                  final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Salary.class);
        final var root = query.from(Salary.class);                                             // FROM Salary AS e
        query.select(root);                                                                    // SELECT e
        query.where(builder.equal(root.get(Salary_.empNo), empNo));                            // WHERE e.empNo = :empNo
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Salary> selectAllByEmpNo4(final @Nonnull EntityManager entityManager, final @Nonnull Employee employee,
                                          final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(employee, "employee is null");
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Salary.class);
        final var root = query.from(Salary.class);                                       // FROM Salary AS e
        query.select(root);                                                              // SELECT e
        query.where(builder.equal(root.get(Salary_.employee), employee));                // WHERE e.employee = :employee
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Salary> findAllByEmpNo(final EntityManager entityManager, final int empNo, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(4)) {
            case 0 -> selectAllByEmpNo1(entityManager, empNo, maxResults);
            case 1 -> selectAllByEmpNo2(entityManager, empNo, maxResults);
            case 2 -> selectAllByEmpNo3(entityManager, empNo, maxResults);
            default -> selectAllByEmpNo4(entityManager, Employee.of(empNo), maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    Salary_IT() {
        super(Salary.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("selectByEmpNo1")
    @Nested
    class SelectByEmpNo1Test {

        @DisplayName("(0)")
        @Test
        void selectByEmpNo1__0() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 0;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNo1(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void selectByEmpNo1__10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 10001;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNo1(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                    .extracting(Salary::getEmpNo)
                    .containsOnly(empNo);
        }
    }

    @ValueSource(ints = {0, 10001})
    @ParameterizedTest
    void selectByEmpNo2__(final int empNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllByEmpNo2(em, empNo, 8));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).satisfiesAnyOf(
                a -> assertThat(a).isEmpty(),
                a -> assertThat(a).isNotEmpty().extracting(Salary::getEmpNo).containsOnly(empNo)
        );
    }

    @ValueSource(ints = {0, 10001})
    @ParameterizedTest
    void selectByEmpNo3__(final int empNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllByEmpNo3(em, empNo, 8));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).satisfiesAnyOf(
                a -> assertThat(a).isEmpty(),
                a -> assertThat(a).isNotEmpty().extracting(Salary::getEmpNo).containsOnly(empNo)
        );
    }

    @ValueSource(ints = {0, 10001})
    @ParameterizedTest
    void selectByEmpNo4__(final int empNo) {
        final var employee = Employee.of(empNo);
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllByEmpNo4(em, employee, 8));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).satisfiesAnyOf(
                a -> assertThat(a).isEmpty(),
                a -> assertThat(a).isNotEmpty()
                        .extracting(Salary::getEmpNo)
                        .containsOnly(empNo),
                a -> assertThat(a).isNotEmpty()
                        .extracting(Salary::getEmployee)
                        .containsOnly(employee)
        );
    }

    @Test
    void selectByEmpNo4__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var empNo = 10001;
        final var employee = applyEntityManager(em -> em.find(Employee.class, empNo));
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> selectAllByEmpNo4(em, employee, 8));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).satisfiesAnyOf(
                a -> assertThat(a).isEmpty(),
                a -> assertThat(a).isNotEmpty()
                        .extracting(Salary::getEmpNo)
                        .containsOnly(empNo),
                a -> assertThat(a).isNotEmpty()
                        .extracting(Salary::getEmployee)
                        .extracting(Employee::getEmpNo)
                        .containsOnly(empNo)
        );
    }
}
