package com.github.jinahya.mysql.employees.persistence;

/**
 * An abstract class for integration-testing {@link Department} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class Department__IT
        extends _BaseEntityIT<Department, String> {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    Department__IT() {
        super(Department.class);
    }
}
