package com.github.jinahya.mysql.employees.persistence.service;

/*-
 * #%L
 * mysql-employees-persistece
 * %%
 * Copyright (C) 2024 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Title;
import com.github.jinahya.mysql.employees.persistence.TitleId;
import com.github.jinahya.mysql.employees.persistence.TitleId_;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public interface ITitleService
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
