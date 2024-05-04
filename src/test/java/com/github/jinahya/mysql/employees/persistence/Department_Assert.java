package com.github.jinahya.mysql.employees.persistence;

import org.assertj.core.api.InstanceOfAssertFactories;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An assertion class for verifying instances of {@link DeptEmp} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public class Department_Assert
        extends _BaseEntity_Assert<Department_Assert, Department, String> {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance for verifying specified actual value.
     *
     * @param actual the actual value to verify.
     */
    Department_Assert(final Department actual) {
        super(actual, Department_Assert.class);
    }

    // ---------------------------------------------------------------------------------------------------------- deptNo

    /**
     * Verifies that {@link #actual actual} value has a {@link Department#getDeptNo() deptNo} property satisfies
     * requirements tested within specified consumer.
     *
     * @param requirements the consumer verifies the {@link Department#getDeptNo() deptNo} property of the
     *                     {@link #actual actual} value.
     * @return this assertion object.
     */
    public Department_Assert hasDeptNoSatisfies(final Consumer<? super String> requirements) {
        isNotNull()
                .extracting(Department::getDeptNo, InstanceOfAssertFactories.STRING)
                .as("deptNo of %s", actual)
                .satisfies(requirements);
        return this;
    }

    /**
     * Verifies that {@link #actual actual} value's {@link Department#getDeptNo() deptNo} property
     * {@link org.assertj.core.api.AbstractAssert#isEqualTo(Object) is equal to} specified value.
     *
     * @param expectedDeptNo the expected value of {@link Department#getDeptNo() deptNo} property.
     * @return this assertion object.
     */
    public Department_Assert hasDeptNo(final String expectedDeptNo) {
        return hasDeptNoSatisfies(dn -> {
            assertThat(dn).isEqualTo(expectedDeptNo);
        });
    }

    // -------------------------------------------------------------------------------------------------------- deptName
    public Department_Assert hasDeptNameSatisfies(final Consumer<? super String> requirements) {
        isNotNull()
                .extracting(Department::getDeptName, InstanceOfAssertFactories.STRING)
                .as("deptName of %s", actual)
                .satisfies(requirements);
        return this;
    }

    /**
     * Verifies that {@link #actual actual} value's {@link Department#getDeptName() deptName} property is equal to
     * specified value.
     *
     * @param expectedDeptName the expected value of {@link Department#getDeptName() deptName}.
     * @return this assertion object.
     */
    public Department_Assert hasDeptName(final String expectedDeptName) {
        return hasDeptNameSatisfies(dn -> {
            assertThat(dn).isEqualTo(expectedDeptName);
        });
    }
}
