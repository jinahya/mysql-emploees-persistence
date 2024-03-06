package com.github.jinahya.mysql.employees.persistence.querydsl;

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.QDepartment;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

class Department_Querydsl_IT extends _BaseEntity_Querydsl_IT<Department, String> {

    Department_Querydsl_IT() {
        super(Department.class);
    }

    @Test
    void selectAll__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var limit = 8;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyEntityManager(em -> {
            return new JPAQuery<Department>(em)
                    .select(QDepartment.department)                                             // SELECT d.*
                    .from(QDepartment.department)                                               // FROM departments AS d
                    .orderBy(QDepartment.department.deptNo.asc())                               // ORDER BY d.dept_no
                    .offset(0)
                    .limit(limit)
                    .fetch();
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .isNotNull()
                .hasSizeLessThanOrEqualTo(limit)
                .isSortedAccordingTo(Comparator.comparing(Department::getDeptNo));
    }

    @ValueSource(strings = {
            "Marketing",
            "Finance"
    })
    @ParameterizedTest
    void findOneByDeptName__(final String deptName) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyEntityManager(em -> {
            return new JPAQuery<Department>(em)
                    .select(QDepartment.department)                                           // SELECT d.*
                    .from(QDepartment.department)                                             // FROM departments AS d
                    .where(QDepartment.department.deptName.eq(deptName))                      // d.dept_name = :deptName
                    .fetchOne();
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isNotNull()
                .extracting(Department::getDeptName)
                .isEqualTo(deptName);
    }
}
