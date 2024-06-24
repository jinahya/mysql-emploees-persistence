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

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

/**
 * An entity class for {@value DeptEmpLatestDate#VIEW_NAME} view.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@NamedQuery(
        name = "DeptEmpLatestDate.selectOneByEmployee",
        query = """
                SELECT e
                FROM DeptEmpLatestDate AS e
                WHERE e.employee = :employee"""
)
@NamedQuery(
        /* DEPRECATE; REMOVE; It's the @Id */
        name = "DeptEmpLatestDate.selectOneByEmpNo",
        query = """
                SELECT e
                FROM DeptEmpLatestDate AS e
                WHERE e.empNo = :empNo"""
)
@Entity
@Table(name = DeptEmpLatestDate.VIEW_NAME)
@Slf4j
public class DeptEmpLatestDate
        extends _BaseEntity<Integer> {

    @Serial
    private static final long serialVersionUID = 4331323808137781879L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the view to which this entity class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "dept_emp_latest_date";

    // ---------------------------------------------------------------------------------------------------------- emp_no

    /**
     * The name of the view column to which the {@link DeptEmpLatestDate_#empNo empNo} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_EMP_NO = DeptEmp.COLUMN_NAME_EMP_NO;

    // ------------------------------------------------------------------------------------------------------- from_date

    /**
     * The name of the view column to which the {@link DeptEmpLatestDate_#fromDate fromDate} attribute maps. The value
     * is {@value}.
     */
    public static final String COLUMN_NAME_FROM_DATE = DeptEmp.COLUMN_NAME_FROM_DATE;

    // --------------------------------------------------------------------------------------------------------- to_date
    // TODO: javadoc
    public static final String COLUMN_NAME_TO_DATE = DeptEmp.COLUMN_NAME_TO_DATE;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    protected DeptEmpLatestDate() {
        super();
    }

    // ------------------------------------------------------------------------------------------------ java.lang.Object

    @Override
    public String toString() {
        return super.toString() + '{' +
                "empNo=" + empNo +
                ",fromDate=" + fromDate +
                ",toDate=" + toDate +
                '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeptEmpLatestDate that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                empNo
        );
    }

    // -------------------------------------------------------------------------------------------------- empNo/employee

    /**
     * Returns current value of {@link DeptEmpLatestDate_#empNo empNo} attribute.
     *
     * @return current value of the {@link DeptEmpLatestDate_#empNo empNo} attribute.
     */
    public Integer getEmpNo() {
        return empNo;
    }

    /**
     * Returns current value of {@link DeptEmpLatestDate_#employee employee} attribute.
     *
     * @return current value of the {@link DeptEmpLatestDate_#employee employee} attribute.
     */
    public Employee getEmployee() {
        return employee;
    }

    // -------------------------------------------------------------------------------------------------------- fromDate

    /**
     * Returns current value of {@link DeptEmpLatestDate_#fromDate fromDate} attribute.
     *
     * @return current value of the {@link DeptEmpLatestDate_#fromDate fromDate} attribute.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    // ---------------------------------------------------------------------------------------------------------- toDate
    // TODO: javadoc
    public LocalDate getToDate() {
        return toDate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_EMP_NO, nullable = false,
//            insertable = false, // eclipselink
            insertable = false,
            updatable = false
    )
    private Integer empNo;

    @Valid
    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude // just for note
    @ToString.Exclude          // just for note
    private Employee employee;

    // -----------------------------------------------------------------------------------------------------------------
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_FROM_DATE, nullable = true, insertable = false, updatable = false)
    private LocalDate fromDate;

    @Basic(optional = true)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = true, insertable = false, updatable = false)
    private LocalDate toDate;
}
