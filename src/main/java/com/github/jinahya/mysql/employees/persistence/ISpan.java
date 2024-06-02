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
        "java:S119" // <TEMPORAL_ACCESSOR ...>
})
public interface ISpan<
        TEMPORAL_ACCESSOR extends TemporalAccessor & Comparable<? super TEMPORAL_ACCESSOR>,
        TEMPORAL_AMOUNT extends TemporalAmount> {

    // ------------------------------------------------------------------------------------------------- Bean-Validation

    /**
     * Asserts that {@link #getStart_() start_} is not after the {@link #getEnd_() end_}.
     *
     * @return {@code true} when {@link #getStart_() start_} is not after the {@link #getEnd_() end_}; {@code false}
     * otherwise; {@code true} when either {@link #getStart_() start_} or {@link #getEnd_() end_} is {@code null}.
     */
    @AssertTrue
    default boolean isStart_IsNotAfterEnd_() {
        final TEMPORAL_ACCESSOR start_ = getStart_();
        final TEMPORAL_ACCESSOR end_ = getEnd_();
        if (start_ == null || end_ == null) {
            return true;
        }
        return start_.compareTo(end_) <= 0;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a temporal amount between {@link #getStart_() start_} and {@link #getEnd_() end_}.
     *
     * @return a temporal amount between {@link #getStart_() start_} and {@link #getEnd_() end_}; {@code null} when
     * either {@link #getStart_() start_} or {@link #getEnd_() end_} is {@code null}.
     */
    TEMPORAL_AMOUNT getSpan_();

    // -----------------------------------------------------------------------------------------------------------------
    TEMPORAL_ACCESSOR getStart_();

    // -----------------------------------------------------------------------------------------------------------------
    TEMPORAL_ACCESSOR getEnd_();
}
