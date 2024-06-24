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

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.InstanceOfAssertFactories;

import java.time.LocalDate;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An assertion class for {@link DeptEmp} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class DeptEmp_Assert
        extends _BaseEntity_Assert<DeptEmp_Assert, DeptEmp, DeptEmpId> {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance for verifying specified actual value.
     *
     * @param actual the actual value to verify.
     */
    DeptEmp_Assert(final DeptEmp actual) {
        super(actual, DeptEmp_Assert.class);
    }

    // -------------------------------------------------------------------------------------------------- empNo/employee

    /**
     * Verifies that the {@link DeptEmp#getEmpNo() empNo} property of the {@link #actual actual} value satisfies
     * requirements tested in specified consumer.
     *
     * @param consumer a consumer for validating the {@link DeptEmp#getEmpNo() empNo} property of the
     *                 {@link #actual actual} value.
     * @return this assertion object.
     */
    public DeptEmp_Assert hasEmpNoSatisfies(final Consumer<? super Integer> consumer) {
        isNotNull()
                .extracting(DeptEmp::getEmpNo, InstanceOfAssertFactories.INTEGER)
                .satisfies(consumer);
        return this;
    }

    /**
     * Verifies that the {@link #actual actual} value has an {@link DeptEmp#getEmpNo() empNo} property
     * {@link AbstractObjectAssert#isEqualTo(Object) equals to} specified value.
     *
     * @param expectedEmpNo the expected value of {@link DeptEmp#getEmpNo() empNo} property.
     * @return this assertion object.
     */
    public DeptEmp_Assert hasEmpNo(final Integer expectedEmpNo) {
        return hasEmpNoSatisfies(en -> assertThat(en).isEqualTo(expectedEmpNo));
    }

    /**
     * Verifies that the {@link DeptEmp#getEmployee() employee} property of the {@link #actual actual} value satisfies
     * requirements tested in specified consumer.
     *
     * @param consumer a consumer for validating the {@link DeptEmp#getEmployee() employee} property of the
     *                 {@link #actual actual} value.
     * @return this assertion object.
     */
    public DeptEmp_Assert hasEmployeeSatisfies(final Consumer<? super Employee> consumer) {
        isNotNull()
                .extracting(DeptEmp::getEmployee, _BaseEntity_Assertions::assertBaseEntity)
                .satisfies(consumer);
        return this;
    }

    /**
     * Verifies that the {@link #actual actual} value has an {@link DeptEmp#getEmployee() employee} property
     * {@link AbstractObjectAssert#isEqualTo(Object) equals to} specified value.
     *
     * @param expectedEmployee the expected value of {@link DeptEmp#getEmployee() employee} property.
     * @return this assertion object.
     */
    public DeptEmp_Assert hasEmployee(final Employee expectedEmployee) {
        return hasEmployeeSatisfies(e -> assertThat(e).isEqualTo(expectedEmployee));
    }

    // ----------------------------------------------------------------------------------------------- deptNo/department
    public DeptEmp_Assert hasDeptNoSatisfies(final Consumer<? super String> consumer) {
        isNotNull()
                .extracting(DeptEmp::getDeptNo, InstanceOfAssertFactories.STRING)
                .satisfies(consumer);
        return this;
    }

    public DeptEmp_Assert hasDepartmentSatisfies(final Consumer<? super Department> consumer) {
        isNotNull()
                .extracting(DeptEmp::getDepartment, _BaseEntity_Assertions::assertBaseEntity)
                .satisfies(consumer);
        return this;
    }

    // -------------------------------------------------------------------------------------------------------- fromDate
    public DeptEmp_Assert hasFromDateSatisfies(final Consumer<? super LocalDate> consumer) {
        isNotNull()
                .extracting(DeptEmp::getFromDate, InstanceOfAssertFactories.LOCAL_DATE)
                .satisfies(consumer);
        return this;
    }

    public DeptEmp_Assert hasFromDate(final LocalDate expectedFromDate) {
        return hasFromDateSatisfies(fd -> {
            assertThat(fd).isEqualTo(expectedFromDate);
        });
    }

    // -------------------------------------------------------------------------------------------------------- toDate
    public DeptEmp_Assert hasToDateSatisfies(final Consumer<? super LocalDate> consumer) {
        isNotNull()
                .extracting(DeptEmp::getToDate, InstanceOfAssertFactories.LOCAL_DATE)
                .satisfies(consumer);
        return this;
    }

    public DeptEmp_Assert hasToDate(final LocalDate expectedToDate) {
        return hasToDateSatisfies(fd -> {
            assertThat(fd).isEqualTo(expectedToDate);
        });
    }
}
