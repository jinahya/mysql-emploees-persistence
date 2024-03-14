package com.github.jinahya.mysql.employees.persistence;

import org.assertj.core.api.InstanceOfAssertFactories;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class Department_Assert extends _BaseEntityAssert<Department_Assert, Department, String> {

    Department_Assert(final Department actual) {
        super(actual, Department_Assert.class);
    }

    @Override
    public Department_Assert hasId(final String expectedId) {
        isNotNull()
                .extracting(Department::getDeptNo, InstanceOfAssertFactories.STRING)
                .as("id of %s", actual)
                .isEqualTo(expectedId);
        return this;
    }

    // ---------------------------------------------------------------------------------------------------------- deptNo
    public Department_Assert hasDeptNo(final String expectedDeptNo) {
        return hasId(expectedDeptNo);
    }

    // -------------------------------------------------------------------------------------------------------- deptName
    public Department_Assert hasDeptNameSatisfies(final Consumer<? super String> requirements) {
        isNotNull()
                .extracting(Department::getDeptName, InstanceOfAssertFactories.STRING)
                .as("deptName of %s", actual)
                .satisfies(requirements);
        return this;
    }

    public Department_Assert hasDeptName(final String expectedDeptName) {
        return hasDeptNameSatisfies(dn -> {
            assertThat(dn).isEqualTo(expectedDeptName);
        });
    }
}
