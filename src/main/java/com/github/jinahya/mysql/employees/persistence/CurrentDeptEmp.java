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
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

/**
 * An entity class for mapping {@value CurrentDeptEmp#VIEW_NAME} view.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@IdClass(CurrentDeptEmpId.class)
@Entity
@Table(name = CurrentDeptEmp.VIEW_NAME)
@Getter
public class CurrentDeptEmp
        extends _BaseEntity<CurrentDeptEmpId> {

    @Serial
    private static final long serialVersionUID = -1037825398322458255L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the view to which this entity class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "current_dept_emp";

    // ---------------------------------------------------------------------------------------------------------- emp_no
    public static final String COLUMN_NAME_EMP_NO = DeptEmpLatestDate.COLUMN_NAME_EMP_NO;

    // --------------------------------------------------------------------------------------------------------- dept_no
    public static final String COLUMN_NAME_DEPT_NO = DeptEmp.COLUMN_NAME_DEPT_NO;

    // ------------------------------------------------------------------------------------------------------- from_date
    public static final String COLUMN_NAME_FROM_DATE = DeptEmpLatestDate.COLUMN_NAME_FROM_DATE;

    // --------------------------------------------------------------------------------------------------------- to_date
    public static final String COLUMN_NAME_TO_DATE = DeptEmpLatestDate.COLUMN_NAME_TO_DATE;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    protected CurrentDeptEmp() {
        super();
    }

    // ------------------------------------------------------------------------------------------------ java.lang.Object

    @Override
    public String toString() {
        return super.toString() + '{' +
                "empNo=" + empNo +
                ",deptNo=" + deptNo +
                ",fromDate=" + fromDate +
                ",toDate=" + toDate +
                '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CurrentDeptEmp that)) {
            return false;
        }
        if (false && !super.equals(obj)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                empNo,
                deptNo
        );
    }

    // ----------------------------------------------------------------------------------------------------------- empNo
    public Integer getEmpNo() {
        return empNo;
    }

    // ---------------------------------------------------------------------------------------------------------- deptNo
    public String getDeptNo() {
        return deptNo;
    }

    // --------------------------------------------------------------------------------------------------------- deptEmp
    public DeptEmp getDeptEmp() {
        return deptEmp;
    }

    // -------------------------------------------------------------------------------------------------------- fromDate

    public LocalDate getFromDate() {
        return fromDate;
    }

    // ---------------------------------------------------------------------------------------------------------- toDate
    public LocalDate getToDate() {
        return toDate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_EMP_NO, nullable = false,
//            insertable = false, // eclipselink
            insertable = true,
            updatable = false)
    private Integer empNo;

    @NotNull
    @Id
    @Column(name = COLUMN_NAME_DEPT_NO, nullable = false,
//            insertable = false, // eclipselink
            insertable = true,
            updatable = false)
    private String deptNo;

    @Valid
    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_EMP_NO, referencedColumnName = DeptEmp.COLUMN_NAME_EMP_NO, nullable = false,
                insertable = false, updatable = false)
    @JoinColumn(name = COLUMN_NAME_DEPT_NO, referencedColumnName = DeptEmp.COLUMN_NAME_DEPT_NO, nullable = false,
                insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private DeptEmp deptEmp;

    // -----------------------------------------------------------------------------------------------------------------
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_FROM_DATE, nullable = true, insertable = false, updatable = false)
    private LocalDate fromDate;

    @Basic(optional = true)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = true, insertable = false, updatable = false)
    private LocalDate toDate;
}
