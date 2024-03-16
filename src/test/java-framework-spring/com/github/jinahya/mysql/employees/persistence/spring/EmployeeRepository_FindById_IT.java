package com.github.jinahya.mysql.employees.persistence.spring;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeRepository_FindById_IT extends EmployeeRepository__IT {

    static List<Integer> selectEmpNoList(final EntityManager entityManager, final @Nullable Integer maxResults) {
        return entityManager
                .createQuery(
                        """
                                SELECT e.empNo
                                FROM Employee AS e
                                ORDER BY e.empNo ASC""",
                        Integer.class
                )
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<Integer> getEmpNoList() {
        return selectEmpNoList(entityManager(), 32);
    }

    @MethodSource({"getEmpNoList"})
    @ParameterizedTest
    void findById__(final int empNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var found = repositoryInstance().findById(empNo);
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found).hasValueSatisfying(v -> {
            assertThat(v.getEmpNo()).isEqualTo(empNo);
        });
    }
}
