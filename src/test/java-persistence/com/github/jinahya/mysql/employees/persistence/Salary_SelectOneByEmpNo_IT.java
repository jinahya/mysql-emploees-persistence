package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class Salary_SelectOneByEmpNo_IT extends Salary_IT {

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
        final var root = query.from(Salary.class);                                           // FROM Salary AS e
        query.select(root);                                                                  // SELECT e
        query.where(builder.equal(root.get(Salary_.empNo), empNo));                          // WHERE e.empNo = :empNo
        query.orderBy(builder.desc(root.get(Salary_.fromDate)));                             // ORDER BY e.fromDate DESC
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Salary> findAllByEmpNo(final EntityManager entityManager, final int empNo, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllByEmpNo1(entityManager, empNo, maxResults);
            case 1 -> selectAllByEmpNo2(entityManager, empNo, maxResults);
            default -> selectAllByEmpNo3(entityManager, empNo, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("selectByEmpNo1")
    @Nested
    class SelectByEmpNo1Test {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 0;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNo1(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
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

    @DisplayName("selectByEmpNo2")
    @Nested
    class SelectByEmpNo2Test {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 0;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNo2(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 10001;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNo2(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                    .extracting(Salary::getEmpNo)
                    .containsOnly(empNo);
        }
    }

    @DisplayName("selectByEmpNo3")
    @Nested
    class SelectByEmpNo3Test {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 0;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNo3(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var empNo = 10001;
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmpNo3(em, empNo, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                    .extracting(Salary::getEmpNo)
                    .containsOnly(empNo);
        }
    }
}
