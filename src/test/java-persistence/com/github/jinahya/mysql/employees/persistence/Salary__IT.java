package com.github.jinahya.mysql.employees.persistence;

/**
 * An abstract class for integration-testing {@link Salary} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class Salary__IT
        extends _BaseEntityIT<Salary, SalaryId> {

    /**
     * Creates a new instance.
     */
    Salary__IT() {
        super(Salary.class);
    }
}
