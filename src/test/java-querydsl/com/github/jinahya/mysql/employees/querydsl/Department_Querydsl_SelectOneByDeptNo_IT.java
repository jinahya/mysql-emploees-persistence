package com.github.jinahya.mysql.employees.querydsl;

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.QDepartment;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Department_Querydsl_SelectOneByDeptNo_IT
        extends Department_Querydsl__IT {

    static List<String> getDeptNoList(final JPAQuery<Department> query, final Long limit) {
        return query.select(QDepartment.department.deptNo)                                       // SELECT dept_no
                .from(QDepartment.department)                                                // FROM departments
                .orderBy(QDepartment.department.deptNo.asc())                                // ORDER BY dept_no ASC
                .limit(Optional.ofNullable(limit).orElse((long) Integer.MAX_VALUE))          // LIMIT ?, ?
                .fetch();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<String> getDeptNoList() {
        return applyQuery(q -> getDeptNoList(q, 32L));
    }

    @MethodSource({"getDeptNoList"})
    @ParameterizedTest
    void __(final String deptNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyQuery(q -> {
            return q.select(QDepartment.department)                                         // SELECT dept_no, dept_name
                    .from(QDepartment.department)                                           // FROM departments
                    .where(QDepartment.department.deptNo.eq(deptNo))                        // WHERE dept_no = ?
                    .fetchOne() // NonUniqueResultException
                    ;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isNotNull()
                .extracting(Department::getDeptNo)
                .isEqualTo(deptNo);
    }

    @MethodSource({"getDeptNoList"})
    @ParameterizedTest
    void __1(final String deptNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyQuery(q -> {
            return _JPQLQueryUtils.fetchOneById(q, QDepartment.department, e -> e.deptNo, deptNo);
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isNotNull()
                .extracting(Department::getDeptNo)
                .isEqualTo(deptNo);
    }

    @MethodSource({"getDeptNoList"})
    @ParameterizedTest
    void __2(final String deptNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyQuery(q -> {
            return _JPQLQueryUtils.fetchOneById(q, QDepartment.department, QDepartment.department.deptNo, deptNo);
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isNotNull()
                .extracting(Department::getDeptNo)
                .isEqualTo(deptNo);
    }
}
