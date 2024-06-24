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
