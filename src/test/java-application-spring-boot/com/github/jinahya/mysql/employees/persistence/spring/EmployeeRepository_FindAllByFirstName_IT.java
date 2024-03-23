package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class EmployeeRepository_FindAllByFirstName_IT
        extends EmployeeRepository__IT {

    private List<Arguments> getFirstNameAndCountArgumentsList() {
        return entityManager()
                .createQuery("""
                                     SELECT e.firstName, COUNT(e) AS c
                                     FROM Employee AS e
                                     GROUP BY e.firstName
                                     ORDER BY c DESC, e.firstName ASC
                                     """,
                             Object[].class)
                .setMaxResults(8)
                .getResultList()
                .stream()
                .map(Arguments::of)
                .toList();
    }

    @MethodSource({"getFirstNameAndCountArgumentsList"})
    @ParameterizedTest
    void __(final String firstName, final long count) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = repositoryInstance().findAllByFirstName(firstName, Pageable.unpaged());
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all.getContent())
                .hasSizeLessThanOrEqualTo(Math.toIntExact(count))
                .isSortedAccordingTo(Comparator.comparing(Employee::getFirstName))
                .extracting(Employee::getFirstName)
                .containsOnly(firstName);
    }
}
