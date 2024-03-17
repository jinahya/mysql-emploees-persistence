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
class Salary_SelectAllByEmployee_IT
        extends Salary__IT {

    private static List<Salary> selectAllByEmployee1(final EntityManager entityManager, final Employee employee,
                                                     final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createNamedQuery("Salary.selectAllByEmployee", Salary.class)
                .setParameter("employee", employee)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectOneByEmployee2(final EntityManager entityManager, final Employee employee,
                                                     final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createQuery("""
                                     SELECT e
                                     FROM Salary AS e
                                     WHERE e.employee = :employee
                                     ORDER BY e.fromDate DESC""",
                             Salary.class)
                .setParameter("employee", employee)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectOneByEmployee3(final EntityManager entityManager, final Employee employee,
                                                     final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Salary.class);
        final var root = query.from(Salary.class);                                       // FROM Salary AS e
        query.select(root);                                                              // SELECT e
        query.where(builder.equal(root.get(Salary_.employee), employee));                // WHERE e.employee = :employee
        query.orderBy(builder.desc(root.get(Salary_.fromDate)));                         // ORDER BY e.fromDate DESC
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Salary> selectAllByEmployee(final EntityManager entityManager, final Employee employee,
                                            final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllByEmployee1(entityManager, employee, maxResults);
            case 1 -> selectOneByEmployee2(entityManager, employee, maxResults);
            default -> selectOneByEmployee3(entityManager, employee, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    @DisplayName("selectAllByEmployee1")
    @Nested
    class SelectByEmpNo1Test {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = Employee.of(0);
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmployee1(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = Employee.of(10001);
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmployee1(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                           .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                           .extracting(Salary::getEmployee)
                           .containsOnly(employee);
        }

        @DisplayName("10002")
        @Test
        void __10002() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = applyEntityManager(em -> em.find(Employee.class, 10002));
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectAllByEmployee1(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                           .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                           .extracting(Salary::getEmployee)
                           .containsOnly(employee);
        }
    }

    @DisplayName("selectAllByEmployee2")
    @Nested
    class SelectByEmpNo2Test {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = Employee.of(0);
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectOneByEmployee2(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = Employee.of(10001);
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectOneByEmployee2(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                           .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                           .extracting(Salary::getEmployee)
                           .containsOnly(employee);
        }

        @DisplayName("10002")
        @Test
        void __10002() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = applyEntityManager(em -> em.find(Employee.class, 10002));
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectOneByEmployee2(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                           .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                           .extracting(Salary::getEmployee)
                           .containsOnly(employee);
        }
    }

    @DisplayName("selectAllByEmployee3")
    @Nested
    class SelectByEmpNo3Test {

        @DisplayName("(0)")
        @Test
        void __0() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = Employee.of(0);
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectOneByEmployee3(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isEmpty();
        }

        @DisplayName("10001")
        @Test
        void __10001() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = Employee.of(10001);
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectOneByEmployee3(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                           .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                           .extracting(Salary::getEmployee)
                           .containsOnly(employee);
        }

        @DisplayName("10002")
        @Test
        void __10002() {
            // --------------------------------------------------------------------------------------------------- given
            final var employee = applyEntityManager(em -> em.find(Employee.class, 10002));
            // ---------------------------------------------------------------------------------------------------- when
            final var all = applyEntityManager(em -> selectOneByEmployee3(em, employee, 8));
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all).isNotEmpty()
                           .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate).reversed())
                           .extracting(Salary::getEmployee)
                           .containsOnly(employee);
        }
    }
}
