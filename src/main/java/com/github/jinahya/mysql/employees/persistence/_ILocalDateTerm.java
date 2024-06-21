package com.github.jinahya.mysql.employees.persistence;

import java.time.LocalDate;
import java.time.Period;

@SuppressWarnings({
        "java:S114" // interface _ILocal...
})
public interface _ILocalDateTerm
        extends _IChronoLocalDateTerm<LocalDate, Period> {

    @Override
    default Period getTermSpan() {
        final var termStart = getTermStart();
        final var termEnd = getTermEnd();
        if (termStart == null || termEnd == null) {
            return null;
        }
        return Period.between(termStart, termEnd);
    }
}
