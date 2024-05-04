package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@org.junit.jupiter.api.Disabled
@Slf4j
class Salary_SelectAll_IT
        extends Salary__IT {

    private static List<Salary> selectAll1(final @Nonnull EntityManager entityManager,
                                           final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createNamedQuery("Salary.selectAll", Salary.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectAll2(final @Nonnull EntityManager entityManager,
                                           final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return entityManager
                .createQuery("""
                                     SELECT e
                                     FROM Salary AS e
                                     ORDER BY e.empNo, e.fromDate""",
                             Salary.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<Salary> selectAll3(final @Nonnull EntityManager entityManager,
                                           final @Nullable Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Salary.class);
        // @formatter:off
        final var root = query.from(Salary.class);                                                   // FROM Salary AS e
        query.select(root);                                                                          // SELECT e
        query.orderBy(                                                                               // ORDER BY
                builder.asc(root.get(Salary_.empNo)),                                                //     e.empNo
                builder.asc(root.get(Salary_.fromDate))                                              //       e.fromDate
        );
        // @formatter:on
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<Salary> selectAll(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAll1(entityManager, maxResults);
            case 1 -> selectAll2(entityManager, maxResults);
            default -> selectAll3(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void selectAll1__() {
        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> {
            final var result = selectAll1(em, maxResults);
            assertThat(result).extracting(super::id).isSorted();
            return result;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).isSortedAccordingTo(
                Comparator.comparing(Salary::getEmpNo)
                        .thenComparing(Salary::getFromDate)
        );
    }

    @Test
    void selectAll2__() {
        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> {
            final var result = selectAll2(em, maxResults);
            assertThat(result).extracting(super::id).isSorted();
            return result;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).isSortedAccordingTo(
                Comparator.comparing(Salary::getEmpNo)
                        .thenComparing(Salary::getFromDate)
        );
    }

    @Test
    void selectAll3__() {
//        final var maxResults = ThreadLocalRandom.current().nextInt(8) + 1;
        final var maxResults = 32;
        final var access = false;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> {
            final var result = selectAll3(em, maxResults);
            assertThat(result).extracting(super::id).isSorted();
            if (access) {
                result.forEach(s -> {
                    final var string = s.getEmployee().toString();
                });
            }
            em.clear();
            return result;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).isSortedAccordingTo(
                Comparator.comparing(Salary::getEmpNo)
                        .thenComparing(Salary::getFromDate)
        );
        if (!access) {
            for (var salary : all) {
                final var string = salary.getEmployee().toString();
            }
        }
    }
}
