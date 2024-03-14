package com.github.jinahya.mysql.employees.querydsl;

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.QDepartment;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class Department_Querydsl_SelectAll_IT extends Department_Querydsl__IT {

    @Test
    void selectAll__() {
        // ------------------------------------------------------------------------------------------------------- given
        final var limit = ThreadLocalRandom.current().nextInt(32) + 1;
        // -------------------------------------------------------------------------------------------------------- when
        final var all = applyQuery(q -> {
            return q.select(QDepartment.department)                                              // SELECT dept_no, ...
                    .from(QDepartment.department)                                                // FROM departments
                    .orderBy(QDepartment.department.deptNo.asc())                                // ORDER BY dept_no ASC
                    .limit(limit)                                                                // LIMIT ?,?
                    .fetch();
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all)
                .hasSizeLessThanOrEqualTo(limit)
                .isSortedAccordingTo(Comparator.comparing(Department::getDeptNo));
    }
}
