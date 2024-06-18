package com.github.jinahya.mysql.employees.persistence;

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAmount;

interface IChronoLocalDateSpanTest<
        SPAN extends _IChronoLocalDateTerm<TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT>,
        TEMPORAL_ACCESSOR extends ChronoLocalDate,
        TEMPORAL_AMOUNT extends TemporalAmount>
        extends ISpanTest<SPAN, TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT> {

}
