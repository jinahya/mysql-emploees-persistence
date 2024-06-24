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
import java.time.Period;

@SuppressWarnings({
        "java:S114" // interface _ILocal...
})
public interface _ILocalDateTerm
        extends _IChronoLocalDateTerm<LocalDate, Period> {

    // ---------------------------------------------------------------------------------------------------------- _ITerm
    @Override
    default Period getTermSpan() {
        final var termStart = getTermStart();
        if (termStart == null) {
            return null;
        }
        final var termEnd = getTermEnd();
        if (termEnd == null) {
            return null;
        }
        return Period.between(termStart, termEnd);
    }
}
