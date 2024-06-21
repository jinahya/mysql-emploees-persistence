package com.github.jinahya.mysql.employees.persistence;

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings({
        "java:S114", // interface _IChrono...
        "java:S119" // <SELF ...>
})
public interface _IChronoLocalDateTerm<
        SELF extends _IChronoLocalDateTerm<SELF, TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT>,
        TEMPORAL_ACCESSOR extends ChronoLocalDate,
        TEMPORAL_AMOUNT extends TemporalAmount>
        extends _ITerm<SELF, TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT> {

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
