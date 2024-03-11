package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Department;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartmentRepository_FindByDeptName_IT extends _BaseEntityRepositoryIT<DepartmentRepository, Department, String> {

    static List<String> selectDeptNameList(final EntityManager entityManager, final @Nullable Integer maxResults) {
        return entityManager.createQuery("SELECT e.deptName FROM Department AS e", String.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    DepartmentRepository_FindByDeptName_IT() {
        super(DepartmentRepository.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<String> getDeptNameList() {
        return selectDeptNameList(entityManager(), 8);
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void __(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var found = repositoryInstance().findByDeptName(deptName);
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                });
    }
}
