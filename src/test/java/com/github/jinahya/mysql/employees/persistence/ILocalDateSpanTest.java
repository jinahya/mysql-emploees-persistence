package com.github.jinahya.mysql.employees.persistence;

import java.time.LocalDate;
import java.time.Period;

interface ILocalDateSpanTest<SPAN extends ILocalDateSpan>
        extends IChronoLocalDateSpanTest<SPAN, LocalDate, Period> {

}