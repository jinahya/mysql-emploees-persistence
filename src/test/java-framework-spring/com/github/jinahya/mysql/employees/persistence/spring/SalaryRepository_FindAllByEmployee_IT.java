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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class SalaryRepository_FindAllByEmployee_IT
        extends SalaryRepository__IT {

    private Page<Employee> getEmployeeList() {
        return employeeRepository.findAll(
                PageRequest.of(
                        0,
                        8,
                        Sort.by(Sort.Order.asc(Employee_.empNo.getName()))
                )
        );
    }

    @MethodSource({"getEmployeeList"})
    @ParameterizedTest
    void __1(final Employee employee) {
        // ------------------------------------------------------------------------------------------------------- given
        final var size = ThreadLocalRandom.current().nextInt(8) + 1;
        final var sort = Sort.by(Sort.Order.asc(Salary_.fromDate.getName()));
        for (var pageable = PageRequest.of(0, size, sort); ; pageable = pageable.next()) {
            // ---------------------------------------------------------------------------------------------------- when
            final var all = repositoryInstance().findAllByEmployee(employee, pageable);
            log.debug("all.content.size: {}", all.getContent().size());
            all.forEach(e -> {
                log.debug("e: {}", e);
            });
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all.getContent())
                    .hasSizeLessThanOrEqualTo(size)
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate))
                    .extracting(Salary::getEmpNo)
                    .containsOnly(employee.getEmpNo()); // <<<<<<<<<<<<<<<<<<
            if (!all.hasNext() || pageable.getPageNumber() > 2) {
                break;
            }
        }
    }

    @Transactional
    @MethodSource({"getEmployeeList"})
    @ParameterizedTest
    void __2(final Employee employee) {
        // ------------------------------------------------------------------------------------------------------- given
        final var size = ThreadLocalRandom.current().nextInt(8) + 1;
        final var sort = Sort.by(Sort.Order.asc(Salary_.fromDate.getName()));
        for (var pageable = PageRequest.of(0, size, sort); ; pageable = pageable.next()) {
            // ---------------------------------------------------------------------------------------------------- when
            final var all = repositoryInstance().findAllByEmployee(employee, pageable);
            log.debug("all.content.size: {}", all.getContent().size());
            all.forEach(e -> {
                log.debug("e: {}", e);
            });
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all.getContent())
                    .hasSizeLessThanOrEqualTo(size)
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate))
                    .extracting(Salary::getEmployee)
                    .containsOnly(employee); // <<<<<<<<<<<<<<<<<<<<<<<<<
            if (!all.hasNext() || pageable.getPageNumber() > 2) {
                break;
            }
        }
    }

    @MethodSource({"getEmployeeList"})
    @ParameterizedTest
    void __3(final Employee employee) {
        // ------------------------------------------------------------------------------------------------------- given
        final var size = ThreadLocalRandom.current().nextInt(8) + 1;
        final var sort = Sort.by(Sort.Order.asc(Salary_.fromDate.getName()));
        for (var pageable = PageRequest.of(0, size, sort); ; pageable = pageable.next()) {
            // ---------------------------------------------------------------------------------------------------- when
            final var all = repositoryInstance().findAll(
                    (r, q, b) -> {
                        if (Long.class != q.getResultType()) {
                            r.fetch(Salary_.employee);
                        }
                        return b.equal(r.join(Salary_.employee), employee);
                    },
                    pageable
            );
            log.debug("all.content.size: {}", all.getContent().size());
            all.forEach(e -> {
                log.debug("e: {}", e);
            });
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all.getContent())
                    .hasSizeLessThanOrEqualTo(size)
                    .isSortedAccordingTo(Comparator.comparing(Salary::getFromDate))
                    .extracting(Salary::getEmployee)
                    .containsOnly(employee);
            if (!all.hasNext() || pageable.getPageNumber() > 2) {
                break;
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Autowired
    private EmployeeRepository employeeRepository;
}