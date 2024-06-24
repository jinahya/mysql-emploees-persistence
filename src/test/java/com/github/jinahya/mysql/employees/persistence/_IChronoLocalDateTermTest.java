package com.github.jinahya.mysql.employees.persistence;

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

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAmount;

interface _IChronoLocalDateTermTest<
        TERM extends _IChronoLocalDateTerm<TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT>,
        TEMPORAL_ACCESSOR extends ChronoLocalDate,
        TEMPORAL_AMOUNT extends TemporalAmount>
        extends _ITermTest<TERM, TEMPORAL_ACCESSOR, TEMPORAL_AMOUNT> {

}
