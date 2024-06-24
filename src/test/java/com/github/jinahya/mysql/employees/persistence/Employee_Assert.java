package com.github.jinahya.mysql.employees.persistence;

/*-
 * #%L
 * mysql-employees-persistece
 * %%
 * Copyright (C) 2024 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.assertj.core.api.InstanceOfAssertFactories;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Objects;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class Employee_Assert
        extends _BaseEntity_Assert<Employee_Assert, Employee, Integer> {

    Employee_Assert(final Employee actual) {
        super(actual, Employee_Assert.class);
    }

    // ----------------------------------------------------------------------------------------------------------- empNo

    // ------------------------------------------------------------------------------------------------------- birthDate
    public Employee_Assert hasBirthDateSatisfying(final Consumer<? super LocalDate> requirements) {
        isNotNull()
                .extracting(Employee::getBirthDate, InstanceOfAssertFactories.LOCAL_DATE)
                .as("birthDate of %s", actual)
                .satisfies(requirements);
        return this;
    }

    public Employee_Assert wasBornIn(final int expectedBirthYear) {
        return hasBirthDateSatisfying(bd -> {
            assertThat(bd).hasYear(expectedBirthYear);
        });
    }

    public Employee_Assert wasBornIn(final Year expectedBirthYear) {
        Objects.requireNonNull(expectedBirthYear, "expectedBirthYear is null");
        return wasBornIn(expectedBirthYear.getValue());
    }

    public Employee_Assert wasBornIn(final Month expectedBirthMonth) {
        return hasBirthDateSatisfying(bd -> {
            assertThat(bd).hasMonth(expectedBirthMonth);
        });
    }

    // ------------------------------------------------------------------------------------------------------- firstName
    public Employee_Assert hasFirstNameSatisfies(final Consumer<? super String> requirement) {
        isNotNull()
                .extracting(Employee::getFirstName, InstanceOfAssertFactories.STRING)
                .satisfies(requirement);
        return this;
    }

    public Employee_Assert hasFirstName(final String expectedFirstName) {
        return hasFirstNameSatisfies(fn -> {
            assertThat(fn).isEqualTo(expectedFirstName);
        });
    }

    // -------------------------------------------------------------------------------------------------------- lastName
    public Employee_Assert hasLastNameSatisfies(final Consumer<? super String> requirement) {
        isNotNull()
                .extracting(Employee::getLastName, InstanceOfAssertFactories.STRING)
                .satisfies(requirement);
        return this;
    }

    public Employee_Assert hasLastName(final String expectedLastName) {
        return hasLastNameSatisfies(fn -> {
            assertThat(fn).isEqualTo(expectedLastName);
        });
    }

    // ---------------------------------------------------------------------------------------------------------- gender
    public Employee_Assert hasGenderSatisfies(final Consumer<? super Employee.Gender> requirement) {
        isNotNull()
                .extracting(Employee::getGender, InstanceOfAssertFactories.type(Employee.Gender.class))
                .satisfies(requirement);
        return this;
    }

    public Employee_Assert hasGender(final Employee.Gender expectedGender) {
        return hasGenderSatisfies(g -> {
            assertThat(g).isSameAs(expectedGender);
        });
    }

    public Employee_Assert isFemale() {
        return hasGender(Employee.Gender.F);
    }

    public Employee_Assert isMale() {
        return hasGender(Employee.Gender.M);
    }

    // ------------------------------------------------------------------------------------------------------- hireDate
    public Employee_Assert hasHireDateSatisfying(final Consumer<? super LocalDate> requirements) {
        isNotNull()
                .extracting(Employee::getHireDate, InstanceOfAssertFactories.LOCAL_DATE)
                .as("hireDate of %s", actual)
                .satisfies(requirements);
        return this;
    }

    public Employee_Assert wasHiredIn(final int expectedHireYear) {
        return hasHireDateSatisfying(hd -> {
            assertThat(hd).hasYear(expectedHireYear);
        });
    }

    public Employee_Assert wasHiredIn(final Year expectedHireYear) {
        Objects.requireNonNull(expectedHireYear, "expectedHireYear is null");
        return wasBornIn(expectedHireYear.getValue());
    }

    public Employee_Assert wasHiredIn(final Month expectedHireMonth) {
        return hasHireDateSatisfying(hd -> {
            assertThat(hd).hasMonth(expectedHireMonth);
        });
    }
}
