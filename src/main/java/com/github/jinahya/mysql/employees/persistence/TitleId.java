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

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

/**
 * An id class for {@link Title} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Embeddable
@Slf4j
public class TitleId
        implements _BaseId {

    @Serial
    private static final long serialVersionUID = -7452067856104640617L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    /**
     * Creates a new instance of specified values.
     *
     * @param empNo    a value for {@link TitleId_#empNo empNo} attribute.
     * @param title    a value for {@link TitleId_#title title} attribute.
     * @param fromDate a value for {@link TitleId_#fromDate fromDate} attribute.
     * @return a new instance of {@code empNo}, {@code title}, and {@code fromDate}.
     */
    public static TitleId of(final Integer empNo, final String title, final LocalDate fromDate) {
        // TODO: implement!
        throw new UnsupportedOperationException("not implemented yet");
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     * <p>
     * 2.4. Primary Keys and Entity Identity (Jakarta Persistence 3.1)
     * <blockquote cite="https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1#a132">The
     * primary key class must be public and <u>must have a public no-arg constructor</u>.</blockquote>
     * <p>
     * 2.4.1. Composite primary keys</a> (Jakarta Persistence 3.2) <blockquote
     * cite="https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2#composite-primary-keys">The
     * primary key class may be <u>a non-abstract regular Java class</u> with <u>a public or protected constructor with
     * no parameters</u>. Alternatively, the primary key class may be <u>any Java record type</u>, in which case it
     * need
     * <u>not have a constructor with no parameters</u>.</blockquote>
     *
     * @see <a href="https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1#a132">2.4. Primary
     * Keys and Entity Identity</a> (Jakarta Persistence 3.1)
     * @see <a
     * href="https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2#composite-primary-keys">2.4.1.
     * Composite primary keys</a> (Jakarta Persistence 3.2)
     */
    public TitleId() {
        super();
    }

    // ------------------------------------------------------------------------------------------------ java.lang.Object

    @Override
    public String toString() {
        return super.toString() + '{' +
                "empNo=" + empNo +
                ",title=" + title +
                ",fromDate=" + fromDate +
                '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TitleId that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(title, that.title) &&
                Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, title, fromDate);
    }

    // --------------------------------------------------------------------------------------------------------- empNo
    public Integer getEmpNo() {
        return empNo;
    }

    // TODO: remove; why?
    @Deprecated
    public void setEmpNo(final Integer empNo) {
        this.empNo = empNo;
    }

    // ----------------------------------------------------------------------------------------------------------- title

    public String getTitle() {
        return title;
    }

    // TODO: remove; why?
    @Deprecated
    public void setTitle(final String title) {
        this.title = title;
    }

    // -------------------------------------------------------------------------------------------------------- fromDate
    public LocalDate getFromDate() {
        return fromDate;
    }

    // TODO: remove; why?
    @Deprecated
    public void setFromDate(final LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = Title.COLUMN_NAME_EMP_NO, nullable = false, insertable = true, updatable = false)
    private Integer empNo;

    @NotNull
    @Basic(optional = false)
    @Column(name = Title.COLUMN_NAME_TITLE, nullable = false, insertable = true, updatable = false)
    private String title;

    @NotNull
    @Basic(optional = false)
    @Column(name = Title.COLUMN_NAME_FROM_DATE, nullable = false, insertable = true, updatable = false)
    private LocalDate fromDate;
}
