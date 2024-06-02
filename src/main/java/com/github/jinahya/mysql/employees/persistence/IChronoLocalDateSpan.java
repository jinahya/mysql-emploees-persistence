package com.github.jinahya.mysql.employees.persistence;

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAmount;

@SuppressWarnings({
        "java:S119" // <TEMPORAL_ACCESSOR ...>
})
public interface IChronoLocalDateSpan<TEMPORAL_ACCESSOR extends ChronoLocalDate, TEMPORAL_AMOUNT extends TemporalAmount>
        extends ISpan<TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT> {

    @Override
    default boolean isStart_IsNotAfterEnd_() {
        if (true) {
            return ISpan.super.isStart_IsNotAfterEnd_();
        }
        final TEMPORAL_ACCESSOR start_ = getStart_();
        final TEMPORAL_ACCESSOR end_ = getEnd_();
        if (start_ == null || end_ == null) {
            return true;
        }
        // TODO: Use ChronoLocalDate#is(Before|After)
        return false;
    }
}
