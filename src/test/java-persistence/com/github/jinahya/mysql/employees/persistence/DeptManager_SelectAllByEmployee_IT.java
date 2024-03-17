package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeptManager_SelectAllByEmployee_IT
        extends _BaseEntityIT<DeptManager, DeptManagerId> {

    static List<Employee> selectDistinctEmployeeList(final EntityManager entityManager) {
        return entityManager
                .createQuery(
                        """
                                SELECT DISTINCT e.employee
                                FROM DeptManager AS e""",
                        Employee.class
                )
                .getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static List<DeptManager> selectAllByEmployee1(final EntityManager entityManager, final Employee employee,
                                                          final Integer maxResults) {
        return entityManager.createNamedQuery("DeptManager.selectAllByEmployee", DeptManager.class)
                            .setParameter("employee", employee)
                            .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                            .getResultList();
    }

    private static List<DeptManager> selectAllByEmployee2(final EntityManager entityManager, final Employee employee,
                                                          final Integer maxResults) {
        return entityManager.createQuery(
                                    """
                                            SELECT e
                                            FROM DeptManager AS e
                                            WHERE e.employee = :employee
                                            ORDER BY e.fromDate DESC""",
                                    DeptManager.class
                            )
                            .setParameter("employee", employee)
                            .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                            .getResultList();
    }

    private static List<DeptManager> selectAllByEmployee3(final EntityManager entityManager, final Employee employee,
                                                          final Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptManager.class);
        final var root = query.from(DeptManager.class);                                      // FROM Department AS e
        query.select(root);                                                                  // SELECT e
        query.where(                                                                         // WHERE
                                                                                             builder.equal(
                                                                                                     //     =
                                                                                                     root.get(
                                                                                                             DeptManager_.employee),
                                                                                                     //     e.id.empNo
                                                                                                     employee
                                                                                                     //     idEmpNo
                                                                                             )
        );
        query.orderBy(builder.desc(root.get(DeptManager_.fromDate)));                        // ORDER BY e.fromDate DESC
        return entityManager.createQuery(query)
                            .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                            .getResultList();
    }

    static List<DeptManager> selectAllByEmployee(final EntityManager entityManager, final Employee employee,
                                                 final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(employee, "employee is null");
        if (maxResults <= 0) {
            throw new IllegalArgumentException("maxResults(" + maxResults + ") is not positive");
        }
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllByEmployee1(entityManager, employee, maxResults);
            case 1 -> selectAllByEmployee1(entityManager, employee, maxResults);
            default -> selectAllByEmployee3(entityManager, employee, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    DeptManager_SelectAllByEmployee_IT() {
        super(DeptManager.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<Employee> getDistinctEmployeeList() {
        return applyEntityManager(
                DeptManager_SelectAllByEmployee_IT::selectDistinctEmployeeList
        );
    }

    @MethodSource({"getDistinctEmployeeList"})
    @ParameterizedTest
    void selectAllByEmployee1__(final Employee employee) {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(16) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(
                em -> selectAllByEmployee1(em, employee, maxResults)
        );
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty()
                .hasSizeLessThanOrEqualTo(maxResults)
                .isSortedAccordingTo(Comparator.comparing(DeptManager::getFromDate, Comparator.reverseOrder()))
                .extracting(DeptManager::getEmployee)
                .containsOnly(employee);
    }

    @MethodSource({"getDistinctEmployeeList"})
    @ParameterizedTest
    void selectAllByEmployee2__(final Employee employee) {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(16) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(
                em -> selectAllByEmployee2(em, employee, maxResults)
        );
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty()
                .hasSizeLessThanOrEqualTo(maxResults)
                .isSortedAccordingTo(Comparator.comparing(DeptManager::getFromDate, Comparator.reverseOrder()))
                .extracting(DeptManager::getEmployee)
                .containsOnly(employee);
    }

    @MethodSource({"getDistinctEmployeeList"})
    @ParameterizedTest
    void selectAllByEmployee3__(final Employee employee) {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(16) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(
                em -> selectAllByEmployee3(em, employee, maxResults)
        );
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty()
                .hasSizeLessThanOrEqualTo(maxResults)
                .isSortedAccordingTo(Comparator.comparing(DeptManager::getFromDate, Comparator.reverseOrder()))
                .extracting(DeptManager::getEmployee)
                .containsOnly(employee);
    }
}