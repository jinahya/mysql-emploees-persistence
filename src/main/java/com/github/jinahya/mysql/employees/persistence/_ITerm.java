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

import jakarta.validation.constraints.AssertTrue;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a term of two temporal accessors.
 *
 * @param <TEMPORAL_ACCESSOR> temporal accessor type parameter
 * @param <TEMPORAL_AMOUNT>   temporal amount type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://blog-ko.engram.us/duration/">기간 영어로 (duration, period, time frame, span, length of time,
 * interval, term, tenure 차이)</a>
 */
@SuppressWarnings({
        "java:S114", // interface _Iterm
        "java:S119" // <TEMPORAL_ACCESSOR ...>
})
public interface _ITerm<
        TEMPORAL_ACCESSOR extends TemporalAccessor & Comparable<? super TEMPORAL_ACCESSOR>,
        TEMPORAL_AMOUNT extends TemporalAmount>
        extends Comparable<_ITerm<?, ?>> {

    /**
     * A comparator compares {@link #getTermStart() termStart} property.
     */
    /* public static final */ Comparator<_ITerm<?, ?>> COMPARING_TERM_START =
            Comparator.comparing(_ITerm::getTermStart);

    // -------------------------------------------------------------------------------------------- java.lang.Comparable

    @Override
    default int compareTo(final _ITerm<?, ?> o) {
        Objects.requireNonNull(o, "o is null");
        return COMPARING_TERM_START.compare(this, o);
    }

    // ----------------------------------------------------------------------------------------- Jakarta Bean Validation

    /**
     * Asserts that {@link #getTermStart() termStart} is not after the {@link #getTermEnd() termEnd}.
     *
     * @return {@code true} when {@link #getTermStart() termStart} is not after the {@link #getTermEnd() termEnd};
     * {@code false} otherwise; {@code true} when either {@link #getTermStart() termStart} or
     * {@link #getTermEnd() termEnd} is {@code null}.
     */
    @AssertTrue
    default boolean isTermStartNotAfterTermEnd() {
        final TEMPORAL_ACCESSOR termStart = getTermStart();
        if (termStart == null) {
            return true;
        }
        final TEMPORAL_ACCESSOR termEnd = getTermEnd();
        if (termEnd == null) {
            return true;
        }
        return termStart.compareTo(termEnd) <= 0;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a temporal amount, of {@link TEMPORAL_AMOUNT}, between {@link #getTermStart() termStart} and
     * {@link #getTermEnd() termEnd}.
     *
     * @return a temporal amount, of {@link TEMPORAL_AMOUNT} between {@link #getTermStart() termStart} and
     * {@link #getTermEnd() termEnd}; {@code null} when either {@link #getTermStart() termStart} or
     * {@link #getTermEnd() termEnd} is {@code null}.
     */
    TEMPORAL_AMOUNT getTermSpan();

    // ------------------------------------------------------------------------------------------------------- termStart

    /**
     * Returns the start of this term.
     *
     * @return the start of this term.
     */
    TEMPORAL_ACCESSOR getTermStart();

    /**
     * Replaces current value of {@code termStart} property with specified value.
     *
     * @param termStart new value for the {@code termStart} property.
     */
    void setTermStart(TEMPORAL_ACCESSOR termStart);

    // --------------------------------------------------------------------------------------------------------- termEnd

    /**
     * Returns the end of this term.
     *
     * @return the end of this term.
     */
    TEMPORAL_ACCESSOR getTermEnd();

    /**
     * Replaces current value of {@code termEnd} property with specified value.
     *
     * @param termEnd new value for the {@code termEnd} property.
     */
    void setTermEnd(TEMPORAL_ACCESSOR termEnd);
}
