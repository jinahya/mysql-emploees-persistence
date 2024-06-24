package com.github.jinahya.mysql.employees.persistence;

/*-
 * #%L
 * mysql-employees-persistece
 * %%
 * Copyright (C) 2024 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
