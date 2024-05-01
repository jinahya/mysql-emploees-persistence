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
class DeptEmp_SelectAllByEmpNo_IT
        extends DeptEmp__IT {

    private static final String PARAMETER_NAME_EMP_NO = "empNo";

    // -----------------------------------------------------------------------------------------------------------------
    private static
    @Nonnull List<DeptEmp> selectAllByEmpNoUsingNamedQuery(final @Nonnull EntityManager entityManager,
                                                           final @Nonnull Integer empNo,
                                                           final @Nullable Integer maxResults) {
        return entityManager
                .createNamedQuery(DeptEmpConstants.NAMED_QUERY_NAME_SELECT_ALL_BY_EMP_NO, DeptEmp.class)
                .setParameter(PARAMETER_NAME_EMP_NO, empNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllByEmpNoUsingQueryLanguage(final @Nonnull EntityManager entityManager,
                                                              final @Nonnull Integer empNo,
                                                              final @Nullable Integer maxResults) {
        return entityManager.createQuery(
                        """
                                SELECT e
                                FROM DeptEmp AS e
                                WHERE e.empNo = :empNo
                                ORDER BY e.fromDate DESC""",
                        DeptEmp.class
                )
                .setParameter(PARAMETER_NAME_EMP_NO, empNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static
    @Nonnull List<DeptEmp> selectAllByEmpNoUsingCriteriaApi(final @Nonnull EntityManager entityManager,
                                                            final @Nonnull Integer empNo,
                                                            final @Nullable Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptEmp.class);
        final var root = query.from(DeptEmp.class);                                       // FROM DeptEmpLatestDate AS e
        query.select(root);                                                               // SELECT e
        query.where(builder.equal(root.get(DeptEmp_.empNo), empNo));                      // WHERE e.empNo = :empNo
        query.orderBy(builder.desc(root.get(DeptEmp_.fromDate)));                         // ORDER BY e.fromDate DESC
        return entityManager.createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static
    @Nonnull List<DeptEmp> selectAllByEmpNo(final @Nonnull EntityManager entityManager, final @Nonnull Integer empNo,
                                            final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllByEmpNoUsingNamedQuery(entityManager, empNo, maxResults);
            case 1 -> selectAllByEmpNoUsingQueryLanguage(entityManager, empNo, maxResults);
            default -> selectAllByEmpNoUsingCriteriaApi(entityManager, empNo, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private <R> R applyEmpNoAndMaxResults(
            final @Nonnull Function<? super Integer, ? extends Function<? super Integer, ? extends R>> function) {
        Objects.requireNonNull(function, "function is null");
        final var empNo = applyEntityManager(DeptEmp__IT::selectRandomEmpNo);
        final var maxResults = ThreadLocalRandom.current().nextBoolean()
                ? null
                : ThreadLocalRandom.current().nextInt(8) + 1;
        return function.apply(empNo).apply(maxResults);
    }

    private <R> R applyEmpNoAndMaxResults(
            final @Nonnull BiFunction<? super Integer, ? super Integer, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return applyEmpNoAndMaxResults(en -> maxResults -> function.apply(en, maxResults));
    }

    private void acceptEmpNoAndMaxResults(
            final @Nonnull Function<? super Integer, ? extends Consumer<? super Integer>> function) {
        Objects.requireNonNull(function, "function is null");
        applyEmpNoAndMaxResults((en, mr) -> {
            function.apply(en).accept(mr);
            return null;
        });
    }

    private void acceptEmpNoAndMaxResults(
            final @Nonnull BiConsumer<? super Integer, ? super Integer> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");
        acceptEmpNoAndMaxResults(en -> mr -> consumer.accept(en, mr));
    }

    private Stream<Arguments> getEmpNoAndMaxResultsArgumentsStream() {
        return IntStream.range(0, 8)
                .mapToObj(i -> applyEmpNoAndMaxResults((en, mr) -> Arguments.of(en, mr)));
    }

    private void verifyResult(final @Nonnull Integer empNo, final @Nullable Integer maxResults,
                              final List<DeptEmp> result) {
        assertThat(result)
                .isNotNull()
                .doesNotContainNull()
                .isSortedAccordingTo(Comparator.comparing(DeptEmp::getFromDate).reversed())
                .extracting(DeptEmp::getEmpNo)
                .containsOnly(empNo);
        if (maxResults != null) {
            assertThat(result)
                    .hasSizeLessThanOrEqualTo(maxResults);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link #selectAllByEmpNoUsingNamedQuery(EntityManager, Integer, Integer)} method.
     */
    @DisplayName("selectAllByEmpNoUsingNamedQuery(entityManager, empNo, maxResults")
    @MethodSource("getEmpNoAndMaxResultsArgumentsStream")
    @ParameterizedTest
    void selectAllByEmpNoUsingNamedQuery__(final int empNo, final Integer maxResults) {
        verifyResult(
                empNo,
                maxResults,
                applyEntityManager(em -> selectAllByEmpNoUsingNamedQuery(em, empNo, maxResults))
        );
    }

    @DisplayName("selectAllByEmpNoUsingQueryLanguage(entityManager, empNo, maxResults")
    @MethodSource("getEmpNoAndMaxResultsArgumentsStream")
    @ParameterizedTest
    void selectAllByEmpNoUsingQueryLanguage__(final int empNo, final Integer maxResults) {
        verifyResult(
                empNo,
                maxResults,
                applyEntityManager(em -> selectAllByEmpNoUsingQueryLanguage(em, empNo, maxResults))
        );
    }

    @DisplayName("selectAllByEmpNoUsingCriteriaApi(entityManager, empNo, maxResults")
    @MethodSource("getEmpNoAndMaxResultsArgumentsStream")
    @ParameterizedTest
    void selectAllByEmpNoUsingCriteriaApi__(final int empNo, final Integer maxResults) {
        verifyResult(
                empNo,
                maxResults,
                applyEntityManager(em -> selectAllByEmpNoUsingCriteriaApi(em, empNo, maxResults))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link #selectAllByEmpNo(EntityManager, Integer, Integer)} method.
     */
    @DisplayName("selectAllByEmpNo(entityManager, empNo, maxResults)")
    @Test
    void selectAllByEmpNo__() {
        acceptEmpNoAndMaxResults(
                (en, mr) -> verifyResult(en, mr, applyEntityManager(em -> selectAllByEmpNo(em, en, mr)))
        );
    }
}
