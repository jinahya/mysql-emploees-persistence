package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
class Salary_SelectAllAvgSalaryByEmployeeGender_IT extends _BaseEntityIT<Salary, SalaryId> {

    private static List<Object[]> selectAllAvgSalaryByEmployeeGender1(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createNamedQuery("Salary.selectAllAvgSalaryByEmployeeGender", Object[].class)
                .getResultList();
    }

    private static List<Object[]> selectAllAvgSalaryByEmployeeGender3(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Object[].class);
        final var salary = query.from(Salary.class);                                           // FROM Salary AS s
        final var employee = salary.join(Salary_.employee);                                    //   JOIN s.employee AS e
        final var gender = employee.get(Employee_.gender);
        query.multiselect(                                                                     // SELECT
                gender,                                                                        //   e.gender,
                builder.avg(salary.get(Salary_.salary))                                        //   AVG(s.salary)
        );
        query.groupBy(gender);                                                                 // GROUP BY e.gender
        query.orderBy(builder.asc(gender));                                                    // ORDER BY e.gender
        return entityManager
                .createQuery(query)
                .getResultList();
    }

    static List<Object[]> selectAllAvgSalaryByEmployeeGender(final EntityManager entityManager) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0, 1 -> selectAllAvgSalaryByEmployeeGender1(entityManager);
            default -> selectAllAvgSalaryByEmployeeGender3(entityManager);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    Salary_SelectAllAvgSalaryByEmployeeGender_IT() {
        super(Salary.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    void selectAllAvgSalaryByEmployeeGender1__() {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(
                Salary_SelectAllAvgSalaryByEmployeeGender_IT::selectAllAvgSalaryByEmployeeGender1
        );
        // -------------------------------------------------------------------------------------------------------- then
        all.forEach(a -> {
            log.debug("{}", Arrays.toString(a));
        });
    }

    @Test
    void selectAllAvgSalaryByEmployeeGender3__() {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(
                Salary_SelectAllAvgSalaryByEmployeeGender_IT::selectAllAvgSalaryByEmployeeGender3
        );
        // -------------------------------------------------------------------------------------------------------- then
        all.forEach(a -> {
            log.debug("{}", Arrays.toString(a));
        });
    }
}
