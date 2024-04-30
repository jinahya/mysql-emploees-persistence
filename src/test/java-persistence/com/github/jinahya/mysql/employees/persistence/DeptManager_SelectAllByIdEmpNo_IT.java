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
class DeptManager_SelectAllByIdEmpNo_IT
        extends _BaseEntityIT<DeptManager, DeptManagerId> {

    static List<Integer> selectDistinctIdEmpNoList(final EntityManager entityManager) {
        return entityManager
                .createQuery(
                        """
                                SELECT DISTINCT e.id.empNo
                                FROM DeptManager AS e""",
                        Integer.class
                )
                .getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static List<DeptManager> selectAllByIdEmpNo1(final EntityManager entityManager, final int idEmpNo,
                                                         final Integer maxResults) {
        return entityManager.createNamedQuery("DeptManager.selectAllByIdEmpNo", DeptManager.class)
                .setParameter("idEmpNo", idEmpNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<DeptManager> selectAllByIdEmpNo2(final EntityManager entityManager, final int idEmpNo,
                                                         final Integer maxResults) {
        return entityManager.createQuery(
                        """
                                SELECT e
                                FROM DeptManager AS e
                                WHERE e.id.empNo = :idEmpNo
                                ORDER BY e.fromDate DESC""",
                        DeptManager.class
                )
                .setParameter("idEmpNo", idEmpNo)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<DeptManager> selectAllByIdEmpNo3(final EntityManager entityManager, final int idEmpNo,
                                                         final Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptManager.class);
        final var root = query.from(DeptManager.class);                                      // FROM Department AS e
        query.select(root);                                                                  // SELECT e
        query.where(                                                                         // WHERE
                                                                                             builder.equal(
                                                                                                     //     =
                                                                                                     root.get(
                                                                                                                     DeptManager_.id)
                                                                                                             .get(DeptManagerId_.empNo),
                                                                                                     //     e.id.empNo
                                                                                                     idEmpNo
                                                                                                     //     idEmpNo
                                                                                             )
        );
        query.orderBy(builder.desc(root.get(DeptManager_.fromDate)));                        // ORDER BY e.fromDate DESC
        return entityManager.createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<DeptManager> selectAllByIdEmpNo(final EntityManager entityManager, final int empNo,
                                                final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> selectAllByIdEmpNo1(entityManager, empNo, maxResults);
            case 1 -> selectAllByIdEmpNo1(entityManager, empNo, maxResults);
            default -> selectAllByIdEmpNo3(entityManager, empNo, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    DeptManager_SelectAllByIdEmpNo_IT() {
        super(DeptManager.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<Integer> getDistinctIdEmpNoList() {
        return applyEntityManager(
                DeptManager_SelectAllByIdEmpNo_IT::selectDistinctIdEmpNoList
        );
    }

    @MethodSource({"getDistinctIdEmpNoList"})
    @ParameterizedTest
    void selectAllByIdEmpNo1__(final int idEmpNo) {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(16) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(
                em -> selectAllByIdEmpNo1(em, idEmpNo, maxResults)
        );
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty()
                .hasSizeLessThanOrEqualTo(maxResults)
                .isSortedAccordingTo(Comparator.comparing(DeptManager::getFromDate, Comparator.reverseOrder()))
                .extracting(DeptManager::getId)
                .extracting(DeptManagerId::getEmpNo)
                .containsOnly(idEmpNo);
    }

    @MethodSource({"getDistinctIdEmpNoList"})
    @ParameterizedTest
    void selectAllByIdEmpNo2__(final int idEmpNo) {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(16) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(
                em -> selectAllByIdEmpNo2(em, idEmpNo, maxResults)
        );
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty()
                .hasSizeLessThanOrEqualTo(maxResults)
                .isSortedAccordingTo(Comparator.comparing(DeptManager::getFromDate, Comparator.reverseOrder()))
                .extracting(DeptManager::getId)
                .extracting(DeptManagerId::getEmpNo)
                .containsOnly(idEmpNo);
    }

    @MethodSource({"getDistinctIdEmpNoList"})
    @ParameterizedTest
    void selectAllByIdEmpNo3__(final int idEmpNo) {
        // ------------------------------------------------------------------------------------------------------- given
        final var maxResults = ThreadLocalRandom.current().nextInt(16) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(
                em -> selectAllByIdEmpNo3(em, idEmpNo, maxResults)
        );
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotEmpty()
                .hasSizeLessThanOrEqualTo(maxResults)
                .isSortedAccordingTo(Comparator.comparing(DeptManager::getFromDate, Comparator.reverseOrder()))
                .extracting(DeptManager::getId)
                .extracting(DeptManagerId::getEmpNo)
                .containsOnly(idEmpNo);
    }
}