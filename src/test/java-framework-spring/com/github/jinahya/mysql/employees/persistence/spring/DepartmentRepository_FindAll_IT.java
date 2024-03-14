package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.Department_;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class DepartmentRepository_FindAll_IT extends DepartmentRepository__IT {

    @Test
    void __() {
        // ------------------------------------------------------------------------------------------------------- given
        final var size = ThreadLocalRandom.current().nextInt(8) + 1;
        final var sort = Sort.by(Sort.Order.asc(Department_.deptNo.getName()));
        for (var p = PageRequest.of(0, size, sort); ; p = p.next()) {
            // ---------------------------------------------------------------------------------------------------- when
            final var all = repositoryInstance().findAll(p);
            // ---------------------------------------------------------------------------------------------------- then
            assertThat(all)
                    .hasSizeLessThanOrEqualTo(size);
            assertThat(all.getContent())
                    .isSortedAccordingTo(Comparator.comparing(Department::getDeptNo));
            if (all.isEmpty()) {
                break;
            }
        }
    }
}
