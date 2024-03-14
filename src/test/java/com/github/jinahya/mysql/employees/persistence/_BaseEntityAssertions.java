package com.github.jinahya.mysql.employees.persistence;

public abstract class _BaseEntityAssertions {

    public static Department_Assert assertBaseEntity(final Department actual) {
        return new Department_Assert(actual);
    }

    public static Employee_Assert assertBaseEntity(final Employee actual) {
        return new Employee_Assert(actual);
    }

    /**
     * Creates a new instance.
     */
    protected _BaseEntityAssertions() {
        super();
    }
}
