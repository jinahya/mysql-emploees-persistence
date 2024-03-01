package com.github.jinahya.mysql.employees.persistence;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Department_Find_IT extends _BaseEntityIT<Department, String> {

    Department_Find_IT() {
        super(Department.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Stream<String> getDeptNoStream() {
        return applyEntityManager(em -> {
            final var all = Department_SelectAll_IT.selectAll(em);
            em.clear();
            return all.stream().map(Department::getDeptNo);
        });
    }

    @MethodSource({"getDeptNoStream"})
    @ParameterizedTest
    void __(final String deptNo) {
        final var found = applyEntityManager(
                em -> em.find(Department.class, deptNo)
        );
        assertThat(found)
                .isNotNull()
                .extracting(Department::getDeptNo)
                .isEqualTo(deptNo);
    }
}
