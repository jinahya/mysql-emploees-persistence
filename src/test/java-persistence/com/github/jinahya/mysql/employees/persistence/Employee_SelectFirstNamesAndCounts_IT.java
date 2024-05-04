package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.Expression;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Employee_SelectFirstNamesAndCounts_IT
        extends _BaseEntityIT<Employee, Integer> {

    private static List<Object[]> selectFirstNamesAndCounts1(final EntityManager entityManager) {
        return entityManager
                .createNamedQuery("Employee.selectFirstNamesAndCounts", Object[].class)
                .getResultList();
    }

    private static List<Object[]> selectFirstNamesAndCounts2(final EntityManager entityManager) {
        return entityManager
                .createQuery(
                        """
                                SELECT e.firstName, COUNT(e.firstName) AS c
                                FROM Employee AS e
                                GROUP BY e.firstName
                                ORDER BY c DESC""",
                        Object[].class
                )
                .getResultList();
    }

    private static List<Object[]> selectFirstNamesAndCounts3(final EntityManager entityManager) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Object[].class);
        final var root = query.from(Employee.class);                                          // FROM Employee AS e
        final var firstName = root.get(Employee_.firstName);
        final var count = builder.count(firstName);
        final var c = count.alias("c");
        query.select(builder.array(                                                           // SELECT
                                                                                              firstName,
                                                                                              // e.firstName,
                                                                                              c
                                                                                              // COUNT(e.firstName) AS c
        ));
        query.groupBy(firstName);                                                             // GROUP BY e.firstName
        query.orderBy(builder.desc((Expression<?>) c));                                       // ORDER BY c DESC
        return entityManager.createQuery(query).getResultList();
    }

    static List<Object[]> selectFirstNamesAndCounts(final EntityManager entityManager) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectFirstNamesAndCounts1(entityManager);
            case 1 -> selectFirstNamesAndCounts2(entityManager);
            default -> selectFirstNamesAndCounts3(entityManager);
        };
    }

    static List<Tuple> selectFirstNamesAndCounts4(final EntityManager entityManager) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Tuple.class);
        final var root = query.from(Employee.class);                                          // FROM Employee AS e
        final var firstName = root.get(Employee_.firstName);
        final var count = builder.count(firstName);
        final var c = count.alias("c");
        query.select(builder.tuple(                                                           // SELECT
                                                                                              firstName,
                                                                                              // e.firstName,
                                                                                              c
                                                                                              // COUNT(e.firstName) AS c
        ));
        query.groupBy(firstName);                                                             // GROUP BY e.firstName
        query.orderBy(builder.desc((Expression<?>) c));                                       // ORDER BY c DESC
        return entityManager.createQuery(query).getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    Employee_SelectFirstNamesAndCounts_IT() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectFirstNamesAndCounts1__() {
        final var list = applyEntityManager(
                Employee_SelectFirstNamesAndCounts_IT::selectFirstNamesAndCounts1
        );
        assertThat(list).extracting(a -> (long) a[1]).isSortedAccordingTo(Comparator.reverseOrder());
        list.forEach(t -> {
            assertThat(t).isNotNull().hasSize(2);
            log.debug("{}({})", t[0], t[1]);
        });
    }

    @Test
    void selectFirstNamesAndCounts2__() {
        final var list = applyEntityManager(
                Employee_SelectFirstNamesAndCounts_IT::selectFirstNamesAndCounts2
        );
        assertThat(list).extracting(a -> (long) a[1]).isSortedAccordingTo(Comparator.reverseOrder());
        list.forEach(t -> {
            assertThat(t).isNotNull().hasSize(2);
            log.debug("{}({})", t[0], t[1]);
        });
    }

    @Test
    void selectFirstNamesAndCounts3__() {
        final var list = applyEntityManager(
                Employee_SelectFirstNamesAndCounts_IT::selectFirstNamesAndCounts3
        );
        assertThat(list).extracting(a -> (long) a[1]).isSortedAccordingTo(Comparator.reverseOrder());
        list.forEach(t -> {
            assertThat(t).isNotNull().hasSize(2);
            log.debug("{}({})", t[0], t[1]);
        });
    }

    @Test
    void selectFirstNamesAndCounts4__() {
        final var list = applyEntityManager(
                Employee_SelectFirstNamesAndCounts_IT::selectFirstNamesAndCounts4
        );
        assertThat(list).extracting(t -> t.get(1, Long.class)).isSortedAccordingTo(Comparator.reverseOrder());
        list.forEach(t -> {
            log.debug("{}({})", t.get(0), t.get(1));
        });
    }
}
