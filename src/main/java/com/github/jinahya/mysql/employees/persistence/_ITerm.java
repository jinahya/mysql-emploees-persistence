package com.github.jinahya.mysql.employees.persistence;

import jakarta.validation.constraints.AssertTrue;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

/**
 * Represents a span.
 *
 * @param <TEMPORAL_ACCESSOR> temporal accessor type parameter
 * @param <TEMPORAL_AMOUNT>   temporal amount type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@SuppressWarnings({
        "java:S114", // interface _Iterm
        "java:S119" // <TEMPORAL_ACCESSOR ...>
})
public interface _ITerm<
        TEMPORAL_ACCESSOR extends TemporalAccessor & Comparable<? super TEMPORAL_ACCESSOR>,
        TEMPORAL_AMOUNT extends TemporalAmount> {

    // ------------------------------------------------------------------------------------------------- Bean-Validation

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
        final TEMPORAL_ACCESSOR termEnd = getTermEnd();
        if (termStart == null || termEnd == null) {
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
    void getTermStart(TEMPORAL_ACCESSOR termStart);

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
    void getTermEnd(TEMPORAL_ACCESSOR termEnd);
}
