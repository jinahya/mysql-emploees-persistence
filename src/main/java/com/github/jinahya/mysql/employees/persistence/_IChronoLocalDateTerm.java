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

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An extended {@link _ITerm} for {@link ChronoLocalDate}.
 *
 * @param <TEMPORAL_ACCESSOR> temporal access type parameter
 * @param <TEMPORAL_AMOUNT>   temporal amount type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@SuppressWarnings({
        "java:S114", // interface _IChrono...
        "java:S119" // <TEMPORAL_ACCESSOR ...>
})
public interface _IChronoLocalDateTerm<
        TEMPORAL_ACCESSOR extends ChronoLocalDate,
        TEMPORAL_AMOUNT extends TemporalAmount>
        extends _ITerm<TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT> {

    // ----------------------------------------------------------------------------------------- Jakarta Bean Validation
    @Override
    default boolean isTermStartNotAfterTermEnd() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return _ITerm.super.isTermStartNotAfterTermEnd();
        }
        final TEMPORAL_ACCESSOR termStart = getTermStart();
        final TEMPORAL_ACCESSOR termEnd = getTermEnd();
        if (termStart == null || termEnd == null) {
            return true;
        }
        return !termStart.isAfter(termEnd);
    }
}
