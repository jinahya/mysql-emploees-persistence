package com.github.jinahya.mysql.employees.persistence;

import java.time.LocalDate;
import java.time.Period;

interface _ILocalDateTermTest<TERM extends _ILocalDateTerm<TERM>>
        extends _IChronoLocalDateTermTest<TERM, LocalDate, Period> {

}
