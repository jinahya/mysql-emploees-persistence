package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Employee_;
import com.github.jinahya.mysql.employees.persistence.Salary;
import com.github.jinahya.mysql.employees.persistence.Salary_;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class SalaryRepository_FindAllByEmpNo_IT extends SalaryRepository__IT {

    private IntStream getEmpNoStream() {
        return employeeRepository.findAll(
                PageRequest.of(
                        0,
                        8,
                        Sort.by(Sort.Order.asc(Employee_.empNo.getName()))
                )
        ).stream().mapToInt(Employee::getEmpNo);
    }

    @MethodSource({"getEmpNoStream"})
    @ParameterizedTest
    void __(final int empNo) {
        // ------------------------------------------------------------------------------------------------------- given
        final var size = ThreadLocalRandom.current().nextInt(8) + 1;
        final var sort = Sort.by(Sort.Order.asc(Salary_.fromDate.getName()));
        for (var pageable = PageRequest.of(0, size, sort); ; pageable = pageable.next()) {
            // ---------------------------------------------------------------------------------------------------- when
            final var all = repositoryInstance().findAllByEmpNo(empNo, pageable);
            log.debug("all.content.size: {}", all.getContent().size());
            all.forEach(e -> {
                log.debug("e: {}", e);
            });
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all.getContent())
                    .hasSizeLessThanOrEqualTo(size)
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate))
                    .extracting(Salary::getEmployee)
                    .extracting(Employee::getEmpNo)
                    .containsOnly(empNo);
            if (!all.hasNext() || pageable.getPageNumber() > 2) {
                break;
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    private EmployeeRepository employeeRepository;
}