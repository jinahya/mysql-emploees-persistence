package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jinahya.mysql.employees.persistence._BaseEntity_Assertions.assertBaseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EntityManager#find(entityClass, primaryKey)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class Department_Find_IT
        extends Department__IT {

    // -----------------------------------------------------------------------------------------------------------------
    private static List<String> selectDeptNoListUsingSql(final EntityManager entityManager,
                                                         final Integer maxResults) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        return __Jdbc_Utils.applyUnwrappedConnection(
                entityManager,
                c -> {
                    final var sql = """
                            SELECT *
                            FROM %1$s
                            ORDER BY %2$s
                            LIMIT %3$d"""
                            .formatted(
                                    Department.TABLE_NAME,
                                    Department.COLUMN_NAME_DEPT_NO,
                                    Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE)
                            );
                    log.debug("sql: {}", sql);
                    try (var statement = c.createStatement();
                         var result = statement.executeQuery(sql)) {
                        final var list = new ArrayList<String>();
                        while (result.next()) {
                            list.add(result.getString(Department.COLUMN_NAME_DEPT_NO));
                        }
                        return list;
                    } catch (final SQLException sqle) {
                        throw new RuntimeException(sqle);
                    }
                });
    }

    private static List<String> selectDeptNoListUsingQueryLanguage(final EntityManager entityManager,
                                                                   final Integer maxResults) {
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

    private static List<String> selectDeptNoListUsingCriteriaApi(final EntityManager entityManager,
                                                                 final Integer maxResults) {
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
            case 0 -> selectDeptNoListUsingSql(entityManager, maxResults);
            case 1 -> selectDeptNoListUsingQueryLanguage(entityManager, maxResults);
            default -> selectDeptNoListUsingCriteriaApi(entityManager, maxResults);
        };
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<String> getDeptNoList() {
        return applyEntityManager(em -> selectDeptNoList(em, 16));
    }

    @DisplayName("entityManager.find(entityClass, primaryKdy)")
    @MethodSource({"getDeptNoList"})
    @ParameterizedTest
    void __(final String deptNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var found = applyEntityManager(
                em -> em.find(Department.class, deptNo)
        );
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found)
                .isNotNull()
                .extracting(Department::getDeptNo)
                .isEqualTo(deptNo);
        assertBaseEntity(found).hasDeptNo(deptNo);
    }
}
