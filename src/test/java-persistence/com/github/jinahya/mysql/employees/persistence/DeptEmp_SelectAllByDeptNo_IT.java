package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class DeptEmp_SelectAllByDeptNo_IT
        extends DeptEmp__IT {

    private static final String PARAMETER_NAME_DEPT_NO = "deptNo";

    // -----------------------------------------------------------------------------------------------------------------
    private static
    @Nonnull List<DeptEmp> selectAllByDeptNoUsingNamedQuery(final @Nonnull EntityManager entityManager,
                                                            final @Nonnull String deptNo,
                                                            final @Nullable Integer maxResults) {
        return entityManager
                .createNamedQuery(DeptEmpConstants.NAMED_QUERY_NAME_SELECT_ALL_BY_DEPT_NO, DeptEmp.class)
                .setParameter(PARAMETER_NAME_DEPT_NO, deptNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllByDeptNoUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                               final @Nonnull String deptNo,
                                                               final @Nullable Integer maxResults) {
        return entityManager.createQuery(
                        """
                                SELECT e
                                FROM DeptEmp AS e
                                WHERE e.deptNo = :deptNo
                                ORDER BY e.fromDate DESC""",
                        DeptEmp.class
                )
                .setParameter(PARAMETER_NAME_DEPT_NO, deptNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllByDeptNoUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                             final @Nonnull String deptNo,
                                                             final @Nullable Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptEmp.class);
        final var root = query.from(DeptEmp.class);                                          // FROM DeptEmp AS e
        query.select(root);                                                                  // SELECT e
        query.where(builder.equal(root.get(DeptEmp_.deptNo), deptNo));                       // WHERE e.deptNo = :deptNo
        query.orderBy(builder.desc(root.get(DeptEmp_.fromDate)));                            // ORDER BY e.fromDate DESC
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static
    @Nonnull List<DeptEmp> selectAllByDeptNo(final @Nonnull EntityManager entityManager, final @Nonnull String deptNo,
                                             final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllByDeptNoUsingNamedQuery(entityManager, deptNo, maxResults);
            case 1 -> selectAllByDeptNoUsingQueryLanguage(entityManager, deptNo, maxResults);
            default -> selectAllByDeptNoUsingCriteriaApi(entityManager, deptNo, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private <R> R applyDeptNoAndMaxResults(
            final @Nonnull Function<? super String, ? extends Function<? super Integer, ? extends R>> function) {
        Objects.requireNonNull(function, "function is null");
        final var deptNo = applyEntityManager(DeptEmp__IT::selectRandomDeptNo);
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        return function.apply(deptNo).apply(maxResults);
    }

    private <R> R applyDeptNoAndMaxResults(
            final @Nonnull BiFunction<? super String, ? super Integer, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyDeptNoAndMaxResults(dn -> maxResults -> function.apply(dn, maxResults));
    }

    private void acceptDeptNoAndMaxResults(
            final @Nonnull Function<? super String, ? extends Consumer<? super Integer>> function) {
        Objects.requireNonNull(function, "function is null");
        applyDeptNoAndMaxResults((dn, mr) -> {
            function.apply(dn).accept(mr);
            return null;
        });
    }

    private void acceptDeptNoAndMaxResults(
            final @Nonnull BiConsumer<? super String, ? super Integer> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");
        acceptDeptNoAndMaxResults(dn -> mr -> consumer.accept(dn, mr));
    }

    private Stream<Arguments> getDeptNoAndMaxResultsArgumentsStream() {
        return IntStream.range(0, 8)
                .mapToObj(i -> applyDeptNoAndMaxResults((dn, mr) -> Arguments.of(dn, mr)));
    }

    private void verifyResult(final @Nonnull String deptNo, final @Nullable Integer maxResults,
                              final List<DeptEmp> result) {
        assertThat(result)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(DeptEmp::getFromDate).reversed())
                .extracting(DeptEmp::getDeptNo)
                .containsOnly(deptNo);
        if (maxResults != null) {
            assertThat(result)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @DisplayName("selectAllByDeptNoUsingNamedQuery(entityManager, deptNo, maxResults")
    @MethodSource("getDeptNoAndMaxResultsArgumentsStream")
    @ParameterizedTest
    void selectAllByDeptNoUsingNamedQuery__(final String deptNo, final Integer maxResults) {
        verifyResult(
                deptNo,
                maxResults,
                applyEntityManager(em -> selectAllByDeptNoUsingNamedQuery(em, deptNo, maxResults))
        );
    }

    @DisplayName("selectAllByDeptNoUsingQueryLanguage(entityManager, deptNo, maxResults")
    @MethodSource("getDeptNoAndMaxResultsArgumentsStream")
    @ParameterizedTest
    void selectAllByDeptNoUsingQueryLanguage__(final String deptNo, final Integer maxResults) {
        verifyResult(
                deptNo,
                maxResults,
                applyEntityManager(em -> selectAllByDeptNoUsingQueryLanguage(em, deptNo, maxResults))
        );
    }

    @DisplayName("selectAllByDeptNoUsingCriteriaApi(entityManager, deptNo, maxResults")
    @MethodSource("getDeptNoAndMaxResultsArgumentsStream")
    @ParameterizedTest
    void selectAllByDeptNoUsingCriteriaApi__(final String deptNo, final Integer maxResults) {
        verifyResult(
                deptNo,
                maxResults,
                applyEntityManager(em -> selectAllByDeptNoUsingCriteriaApi(em, deptNo, maxResults))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    @DisplayName("selectAllByDeptNo(entityManager, deptNo, maxResults)")
    @Test
    void selectAllByDeptNo__() {
        acceptDeptNoAndMaxResults(
                (dn, mr) -> verifyResult(dn, mr, applyEntityManager(em -> selectAllByDeptNo(em, dn, mr)))
        );
    }
}
