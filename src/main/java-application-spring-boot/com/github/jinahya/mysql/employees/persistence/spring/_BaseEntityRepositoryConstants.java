package com.github.jinahya.mysql.employees.persistence.spring;

@SuppressWarnings({
        "java:S101" // class _Base...
})
public final class _BaseEntityRepositoryConstants {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A param name of {@value}.
     */
    public static final String PARAM_NAME_YEAR = "year";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PARAM_NAME_MONTH = "month";

    public static final int PARAM_VALUE_MONTH_MIN = 1;

    public static final int PARAM_VALUE_MONTH_MAX = 12;

    // -----------------------------------------------------------------------------------------------------------------
    private _BaseEntityRepositoryConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
