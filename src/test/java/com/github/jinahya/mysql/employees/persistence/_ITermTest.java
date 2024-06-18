package com.github.jinahya.mysql.employees.persistence;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

interface _ITermTest<
        ISPAN extends _ITerm<TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT>,
        TEMPORAL_ACCESSOR extends TemporalAccessor & Comparable<? super TEMPORAL_ACCESSOR>,
        TEMPORAL_AMOUNT extends TemporalAmount> {

}
