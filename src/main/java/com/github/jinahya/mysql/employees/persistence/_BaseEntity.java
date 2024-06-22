package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;

/**
 * An abstract mapped superclass for concrete entities.
 *
 * @param <ID> id type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@MappedSuperclass
@SuppressWarnings({
        "java:S101", // class _BaseEntity
        "java:S119"  // <ID ...>
})
public abstract class _BaseEntity<ID extends Serializable>
        implements Serializable {

    @Serial
    private static final long serialVersionUID = -3812806986886347446L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    protected _BaseEntity() {
        super();
    }

    // ------------------------------------------------------------------------------------------------ java.lang.Object

    @Override
    public String toString() {
        return super.toString() + '{' +
                '}';
    }

//    @Override
//    public boolean equals(final Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        return obj instanceof _BaseEntity<?>;
//    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
