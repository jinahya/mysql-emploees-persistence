package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.QDepartment;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartmentRepository_FindByDeptName_IT extends DepartmentRepository__IT {

    private static List<String> selectDeptNameList(final EntityManager entityManager,
                                                   final @Nullable Integer maxResults) {
        return entityManager
                .createQuery("SELECT e.deptName FROM Department AS e ORDER BY e.deptName ASC", String.class)
                .setMaxResults(Optional.ofNullable(maxResults).orElse(Integer.MAX_VALUE))
                .getResultList();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<String> getDeptNameList() {
        return selectDeptNameList(entityManager(), 32);
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void findByDeptName__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var found = repositoryInstance().findByDeptName(deptName);
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                });
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void findOne__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var found = repositoryInstance().findOne(QDepartment.department.deptName.eq(deptName));
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(found)
                .hasValueSatisfying(v -> {
                    assertThat(v.getDeptName()).isEqualTo(deptName);
                });
    }
}
