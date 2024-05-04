package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jinahya.mysql.employees.persistence._BaseEntity_Assertions.assertBaseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EntityManager#find(entityClass, primaryKey)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Department_Find_IT
        extends Department__IT {

    private static List<String> selectDeptNoList1(final EntityManager entityManager, final Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e.deptNo
                                FROM Department AS e
                                ORDER BY e.deptNo ASC""",
                        String.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    private static List<String> selectDeptNoList2(final EntityManager entityManager, final Integer maxResults) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(String.class);
        final var root = query.from(Department.class);                                          // FROM Department AS e
        final var deptNo = root.get(Department_.deptNo);
        query.select(deptNo);                                                                   // SELECT e.deptNo
        query.orderBy(builder.asc(deptNo));                                                     // ORDER BY e.deptNo ASC
        return entityManager
                .createQuery(query)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    static List<String> selectDeptNoList(final EntityManager entityManager, final Integer maxResults) {
        return switch (ThreadLocalRandom.current().nextInt(2)) {
            case 0 -> selectDeptNoList1(entityManager, maxResults);
            default -> selectDeptNoList2(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<String> getDeptNoList() {
        return applyEntityManager(em -> selectDeptNoList(em, 16));
    }

    @MethodSource({"getDeptNoList"})
    @ParameterizedTest
    void __(final String deptNo) {
        final var found = applyEntityManager(
                em -> em.find(Department.class, deptNo)
        );
        assertThat(found)
                .isNotNull()
                .extracting(Department::getDeptNo)
                .isEqualTo(deptNo);
        assertBaseEntity(found).hasDeptNo(deptNo);
    }
}
