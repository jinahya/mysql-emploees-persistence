package com.github.jinahya.mysql.employees.persistence.spring;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

import static com.github.jinahya.mysql.employees.persistence._BaseEntityAssertions.assertBaseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartmentRepository_FindById_IT
        extends DepartmentRepository__IT {

    static List<String> selectDeptNoList(final EntityManager entityManager, final @Nullable Integer maxResults) {
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

    // -----------------------------------------------------------------------------------------------------------------
    private List<String> getDeptNoList() {
        return selectDeptNoList(entityManager(), 32);
    }

    @DisplayName("findById(deptNo)")
    @MethodSource({"getDeptNoList"})
    @ParameterizedTest
    void __(final String deptNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var found = repositoryInstance().findById(deptNo);
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found).hasValueSatisfying(v -> {
            assertThat(v.getDeptNo()).isEqualTo(deptNo);
            assertBaseEntity(v).hasDeptNo(deptNo);
        });
    }

    @DisplayName("findById(unknown)empty")
    @Test
    void _Empty_Unknown() {
        // ------------------------------------------------------------------------------------------------------- given
        final var deptNo = "d000";
        // -------------------------------------------------------------------------------------------------------- when
        final var found = repositoryInstance().findById(deptNo);
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found).isEmpty();
    }
}
