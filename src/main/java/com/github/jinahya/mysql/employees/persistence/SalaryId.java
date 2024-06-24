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

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

/**
 * An id class for the {@link Salary} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class SalaryId
        implements _BaseId,
                   Comparable<SalaryId> {

    @Serial
    private static final long serialVersionUID = -378954798191441067L;

    // -----------------------------------------------------------------------------------------------------------------
    public static final Comparator<SalaryId> COMPARING_EMP_NO = Comparator.comparing(SalaryId::getEmpNo);

    public static final Comparator<SalaryId> COMPARING_FROM_DATE = Comparator.comparing(SalaryId::getFromDate);

    static final Comparator<SalaryId> COMPARING_EMP_NO_FROM_DATE = COMPARING_EMP_NO.thenComparing(COMPARING_FROM_DATE);

    // ------------------------------------------------------------------------------------------------ java.lang.Object
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SalaryId that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(empNo, fromDate);
    }

    // -------------------------------------------------------------------------------------------- java.lang.Comparable

    @Override
    public int compareTo(final SalaryId o) {
        Objects.requireNonNull(o, "o is null");
        return COMPARING_EMP_NO_FROM_DATE.compare(this, o);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    private Integer empNo;

    @NotNull
    private LocalDate fromDate;
}
