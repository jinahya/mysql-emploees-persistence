package com.github.jinahya.mysql.employees.persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Employee_Persist_IT extends _BaseEntityIT<Employee, Integer> {

    // -----------------------------------------------------------------------------------------------------------------
    Employee_Persist_IT() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Test
    void persistFindRemove() {
        applyEntityManagerInTransaction(em -> {
            // ----------------------------------------------------------------------------------------------------- new
            final var instance = newEntityInstance();
            instance.setEmpNo(Employee_SelectMaxEmpNo_IT.getNextEmpNo(em));
            instance.setBirthDate(LocalDate.now().minusYears(20L)); // TODO: Use your own
            instance.setFirstName("First");                         // TODO: Use your own
            instance.setLastName("Last");                           // TODO: Use your own
            instance.setGender(                                     // TODO: Use your own
                    ThreadLocalRandom.current().nextBoolean() ? Employee.Gender.M : Employee.Gender.F
            );
            instance.setHireDate(LocalDate.now());
            // ------------------------------------------------------------------------------------------------- persist
            log.debug("persisting {}", instance);
            em.persist(instance);
            em.flush();
            em.clear();
            // ---------------------------------------------------------------------------------------------------- find
            final var found = em.find(Employee.class, identifier(instance));
            log.debug("found: {}", found);
            assertThat(found).isNotNull().satisfies(f -> {
                assertThat(f.getEmpNo()).isEqualTo(instance.getEmpNo());
                assertThat(f.getBirthDate()).isEqualTo(instance.getBirthDate());
                assertThat(f.getFirstName()).isEqualTo(instance.getFirstName());
                assertThat(f.getLastName()).isEqualTo(instance.getLastName());
                assertThat(f.getGender()).isEqualTo(instance.getGender());
                assertThat(f.getHireDate()).isEqualTo(instance.getHireDate());
            });
            // -------------------------------------------------------------------------------------------------- remove
            log.debug("removing found...");
            em.remove(found);
            em.flush();
            // --------------------------------------------------------------------------------------------- find, again
            log.debug("finding, again...");
            assertThat(em.find(Employee.class, identifier(instance))).isNull();
            return null;
        });
    }
}
