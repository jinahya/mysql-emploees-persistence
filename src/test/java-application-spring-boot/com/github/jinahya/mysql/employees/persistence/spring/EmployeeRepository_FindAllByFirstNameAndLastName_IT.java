package com.github.jinahya.mysql.employees.persistence.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class EmployeeRepository_FindAllByFirstNameAndLastName_IT
        extends EmployeeRepository__IT {

    private List<Arguments> getFirstNameAndCountArgumentsList() {
        return entityManager()
                .createQuery("""
                                     SELECT e.firstName, e.lastName, COUNT(e) AS c
                                     FROM Employee AS e
                                     GROUP BY e.firstName, e.lastName
                                     ORDER BY c DESC, e.firstName ASC, e.lastName ASC""",
                             Object[].class)
                .setMaxResults(8)
                .getResultList()
                .stream()
                .map(Arguments::of)
                .toList();
    }

    @MethodSource({"getFirstNameAndCountArgumentsList"})
    @ParameterizedTest
    void __(final String firstName, final String lastName, final long count) {
        // -------------------------------------------------------------------------------------------------------- when
        final var all = repositoryInstance().findAllByFirstNameAndLastName(firstName, lastName, Pageable.unpaged());
        // -------------------------------------------------------------------------------------------------------- then
        // TODO: verify!
    }

    @DisplayName("Jason Bourne")
    @Test
    void __() {
        // ------------------------------------------------------------------------------------------------------- given
        final var firstName = "Jason";
        final var lastName = "Bourne";
        // -------------------------------------------------------------------------------------------------------- when
        final var all = repositoryInstance().findAllByFirstNameAndLastName(firstName, lastName, Pageable.unpaged());
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all).isEmpty();
    }
}
