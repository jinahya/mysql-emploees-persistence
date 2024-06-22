package com.github.jinahya.mysql.employees.persistence;

import java.time.LocalDate;

/**
 * Constants for domain classes.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@SuppressWarnings({
        "java:S101" // class _Domain...
})
public final class _BaseEntityConstants {

    // ------------------------------------------------------------------------------------------------------- from_date

    /**
     * A column name of {@value}.
     */
    public static final String COLUMN_NAME_FROM_DATE = "from_date";

    // --------------------------------------------------------------------------------------------------------- to_date

    /**
     * A column name of {@value}.
     */
    public static final String COLUMN_NAME_TO_DATE = "to_date";

    /**
     * A value of the {@link #COLUMN_NAME_TO_DATE}, which represents an unspecified value.
     */
    public static final String COLUMN_VALUE_TO_DATE_UNSPECIFIED = "9999-01-01";

    /**
     * A value for an attribute maps to the {@link #COLUMN_NAME_TO_DATE}, which represents an unspecified value.
     *
     * @see #COLUMN_VALUE_TO_DATE_UNSPECIFIED
     * @see LocalDate#parse(CharSequence)
     */
    public static final LocalDate ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED =
            LocalDate.parse(COLUMN_VALUE_TO_DATE_UNSPECIFIED);

    // -----------------------------------------------------------------------------------------------------------------
    private _BaseEntityConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
