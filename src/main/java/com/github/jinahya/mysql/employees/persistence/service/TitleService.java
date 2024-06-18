package com.github.jinahya.mysql.employees.persistence.service;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Title;
import com.github.jinahya.mysql.employees.persistence.TitleId;
import com.github.jinahya.mysql.employees.persistence.TitleId_;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public interface TitleService
        extends _IBaseEntityService<Title, TitleId> {

    /**
     * Sets specified employee's title as specified from specified date, while setting specified employee's latest
     * title's {@link com.github.jinahya.mysql.employees.persistence.TitleId_#fromDate id.fromDate} with specified
     * date.
     *
     * @param employee the {@code employee}.
     * @param title    a value of {@link TitleId_#title id.title} attribute.
     * @param fromDate a value of {@link TitleId_#fromDate id.fromDate} attribute.
     * @return a new instance of {@link Title}.
     */
    Title setTitle(@Valid @NotNull Employee employee, @NotBlank String title, @NotNull LocalDate fromDate);
}
