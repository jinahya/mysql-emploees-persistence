package com.github.jinahya.mysql.employees.persistence;

import java.time.LocalDate;

public final class _DomainConstants {

    // ------------------------------------------------------------------------------------------------------- from_date
    public static final String COLUMN_NAME_FROM_DATE = "from_date";

    // --------------------------------------------------------------------------------------------------------- to_date
    public static final String COLUMN_NAME_TO_DATE = "to_date";

    public static final LocalDate ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED = LocalDate.parse("9999-01-01");

    // -----------------------------------------------------------------------------------------------------------------
    private _DomainConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
