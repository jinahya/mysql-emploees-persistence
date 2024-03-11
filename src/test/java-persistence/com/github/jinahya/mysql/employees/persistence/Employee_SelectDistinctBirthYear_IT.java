package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Employee_SelectDistinctBirthYear_IT extends _BaseEntityIT<Employee, Integer> {

    private static List<Integer> selectDistinctBirthYear1(final EntityManager entityManager) {
        return entityManager
                .createNamedQuery("Employee.selectDistinctBirthYear", Integer.class)
                .getResultList();
    }

    private static List<Integer> selectDistinctBirthYear2(final EntityManager entityManager) {
        return entityManager
                .createQuery("""
                                SELECT DISTINCT EXTRACT(YEAR FROM e.birthDate) AS birthYear
                                FROM Employee AS e
                                ORDER BY birthYear"""
                        , Integer.class)
                .getResultList();
    }

//    private static List<Integer> selectDistinctBirthYear3(final EntityManager entityManager) {
//        final var builder = entityManager.getCriteriaBuilder();
//        final var query = builder.createQuery(Integer.class);
//        final var root = query.from(Employee.class);                                   // FROM Employee AS e
//        try {
//            query.select(                                                              // SELECT
//                    builder.extract(LocalDateField.YEAR,                               // EXTRACT(YEAR FROM e.birthDate)
//                            root.get(Employee_.birthDate))
//            ).distinct(true);                                                          // DISTINCT
//        } catch (final AbstractMethodError ame) {
//            ame.printStackTrace();
//            return Collections.emptyList();
//        }
//        return entityManager.createQuery(query).getResultList();
//    }

//    static List<Integer> selectDistinctBirthYear(final EntityManager entityManager) {
//        Objects.requireNonNull(entityManager, "entityManager is null");
//        return switch (ThreadLocalRandom.current().nextInt(3)) {
//            case 0 -> selectDistinctBirthYear1(entityManager);
//            case 1 -> selectDistinctBirthYear2(entityManager);
//            default -> selectDistinctBirthYear3(entityManager);
//        };
//    }

    // -----------------------------------------------------------------------------------------------------------------
    Employee_SelectDistinctBirthYear_IT() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectDistinctBirthYear1__() {
        final var all = applyEntityManager(
                Employee_SelectDistinctBirthYear_IT::selectDistinctBirthYear1
        );
        assertThat(all).doesNotHaveDuplicates();
    }

    @Test
    void selectDistinctBirthYear2__() {
        final var all = applyEntityManager(
                Employee_SelectDistinctBirthYear_IT::selectDistinctBirthYear2
        );
        assertThat(all).doesNotHaveDuplicates();
    }

//    @Test
//    void selectDistinctBirthYear3__() {
//        final var all = applyEntityManager(
//                Employee_SelectDistinctBirthYear_IT::selectDistinctBirthYear3
//        );
//        assertThat(all).doesNotHaveDuplicates();
//    }
}
