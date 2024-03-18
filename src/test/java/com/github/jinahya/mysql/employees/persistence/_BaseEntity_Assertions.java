package com.github.jinahya.mysql.employees.persistence;

/**
 * An assertion factory for verifying instances of {@link _BaseEntity} class.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public abstract class _BaseEntity_Assertions {

    /**
     * Returns an assertion object for verifying specified actual value.
     *
     * @param actual the actual value to verify.
     * @return an assertion object for verifying {@code actual}.
     */
    public static DeptEmp_Assert assertBaseEntity(final DeptEmp actual) {
        return new DeptEmp_Assert(actual);
    }

    public static Department_Assert assertBaseEntity(final Department actual) {
        return new Department_Assert(actual);
    }

    public static Employee_Assert assertBaseEntity(final Employee actual) {
        return new Employee_Assert(actual);
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    protected _BaseEntity_Assertions() {
        super();
    }
}
