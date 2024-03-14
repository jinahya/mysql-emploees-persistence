package com.github.jinahya.mysql.employees.querydsl;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.QEmployee;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Employee_Querydsl_SelectOneByEmpNo_IT extends Employee_Querydsl__IT {

    static List<Integer> getEmpNoList(final JPAQuery<Employee> query, final Long limit) {
        return query.select(QEmployee.employee.empNo)                                             // SELECT emp_no
                    .from(QEmployee.employee)                                                     // FROM employees
                    .orderBy(QEmployee.employee.empNo.asc())                                      // ORDER BY emp_no ASC
                    .limit(Optional.ofNullable(limit).orElse((long) Integer.MAX_VALUE))           // LIMIT ?, ?
                    .fetch();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private List<Integer> getEmpNoList() {
        return applyQuery(q -> getEmpNoList(q, 32L));
    }

    @MethodSource({"getEmpNoList"})
    @ParameterizedTest
    void __(final Integer empNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyQuery(q -> {
            return q.select(QEmployee.employee)                                              // SELECT emp_no, dept_name
                    .from(QEmployee.employee)                                                // FROM employees
                    .where(QEmployee.employee.empNo.eq(empNo))                               // WHERE emp_no = ?
                    .fetchOne() // NonUniqueResultException
                    ;
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isNotNull()
                .extracting(Employee::getEmpNo)
                .isEqualTo(empNo);
    }

    @MethodSource({"getEmpNoList"})
    @ParameterizedTest
    void __1(final Integer empNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyQuery(q -> {
            return _JPQLQueryUtils.fetchOneById(q, QEmployee.employee, e -> e.empNo, empNo);
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isNotNull()
                .extracting(Employee::getEmpNo)
                .isEqualTo(empNo);
    }

    @MethodSource({"getEmpNoList"})
    @ParameterizedTest
    void __2(final Integer empNo) {
        // -------------------------------------------------------------------------------------------------------- when
        final var one = applyQuery(q -> {
            return _JPQLQueryUtils.fetchOneById(q, QEmployee.employee, QEmployee.employee.empNo, empNo);
        });
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(one)
                .isNotNull()
                .extracting(Employee::getEmpNo)
                .isEqualTo(empNo);
    }
}
