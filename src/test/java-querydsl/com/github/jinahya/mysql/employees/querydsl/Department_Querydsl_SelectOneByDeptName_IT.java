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
class Department_Querydsl_SelectOneByDeptName_IT
        extends Department_Querydsl__IT {

    static List<String> getDeptNameList(final JPAQuery<Department> query, final Long limit) {
        return query.select(QDepartment.department.deptName)                                     // SELECT dept_name
                .from(QDepartment.department)                                                // FROM departments
                .orderBy(QDepartment.department.deptNo.asc())                                // ORDER BY dept_no ASC
                .limit(Optional.ofNullable(limit).orElse((long) Integer.MAX_VALUE))          // LIMIT ?, ?
                .fetch();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<String> getDeptNameList() {
        return applyQuery(q -> getDeptNameList(q, 32L));
    }

    @MethodSource({"getDeptNameList"})
    @ParameterizedTest
    void __(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyQuery(q -> {
            return q.select(QDepartment.department)                                         // SELECT dept_no, dept_name
                    .from(QDepartment.department)                                           // FROM departments
                    .where(QDepartment.department.deptName.eq(deptName))                    // WHERE dept_name = ?
                    .fetchOne() // NonUniqueResultException
                    ;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isNotNull()
                .extracting(Department::getDeptName)
                .isEqualTo(deptName);
    }
}
