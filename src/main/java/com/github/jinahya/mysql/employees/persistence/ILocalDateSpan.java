package com.github.jinahya.mysql.employees.persistence;

import java.time.LocalDate;
import java.time.Period;

public interface ILocalDateSpan
        extends IChronoLocalDateSpan<LocalDate, Period> {

    @Override
    default Period getSpan_() {
        final var start_ = getStart_();
        final var end_ = getEnd_();
        if (start_ == null || end_ == null) {
            return null;
        }
        return Period.between(start_, end_);
    }
}
