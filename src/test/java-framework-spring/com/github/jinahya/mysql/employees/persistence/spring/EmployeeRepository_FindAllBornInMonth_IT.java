package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Employee_;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class EmployeeRepository_FindAllBornInMonth_IT extends EmployeeRepository__IT {

    // -----------------------------------------------------------------------------------------------------------------
    @EnumSource(Month.class)
    @ParameterizedTest
    void __(final Month month) {
        final int page = 0;
        final int size = ThreadLocalRandom.current().nextInt(16) + 1;
        final var sort = Sort.by(Sort.Order.asc(Employee_.empNo.getName()));
        final var pageable = PageRequest.of(0, size, sort);
        // -------------------------------------------------------------------------------------------------------- when
        final var all = repositoryInstance().findAllBornIn(month, pageable);
        // -------------------------------------------------------------------------------------------------------- then
        assertThat(all.getContent())
                .hasSizeLessThanOrEqualTo(size)
                .isSortedAccordingTo(Comparator.comparing(Employee::getEmpNo))
                .extracting(Employee::getBirthDate)
                .extracting(LocalDate::getMonth)
                .containsOnly(month);
    }
}
