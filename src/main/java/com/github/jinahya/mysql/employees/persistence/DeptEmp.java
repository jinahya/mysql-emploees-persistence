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
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * An entity class maps {@value DeptEmp#TABLE_NAME} table.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see DeptEmpId
 * @see Department
 * @see Employee
 */
@NamedEntityGraph(
        name = "DeptEmp.department",
        attributeNodes = {
                @NamedAttributeNode(DeptEmp.ATTRIBUTE_NAME_DEPARTMENT)
        }
)
@NamedEntityGraph(
        name = "DeptEmp.employee",
        attributeNodes = {
                @NamedAttributeNode(DeptEmp.ATTRIBUTE_NAME_EMPLOYEE)
        }
)
@NamedEntityGraph(
        name = "DeptEmp.employeeAndDepartment",
        attributeNodes = {
                @NamedAttributeNode(DeptEmp.ATTRIBUTE_NAME_EMPLOYEE),
                @NamedAttributeNode(DeptEmp.ATTRIBUTE_NAME_DEPARTMENT)
        }
)
@NamedQuery(
        name = "DeptEmp.selectAllByDepartment",
        query = """
                SELECT e
                FROM DeptEmp AS e
                WHERE e.department = :department
                order by e.fromDate DESC"""
)
@NamedQuery(
        name = DeptEmpConstants.NAMED_QUERY_NAME_SELECT_ALL_BY_DEPT_NO,
        query = """
                SELECT e
                FROM DeptEmp AS e
                WHERE e.deptNo = :deptNo
                ORDER BY e.fromDate DESC"""
)
@NamedQuery(
        name = "DeptEmp.selectAllByEmployee",
        query = """
                SELECT e
                FROM DeptEmp AS e
                WHERE e.employee = :employee
                ORDER BY e.fromDate DESC"""
)
@NamedQuery(
        name = DeptEmpConstants.NAMED_QUERY_NAME_SELECT_ALL_BY_EMP_NO,
        query = """
                SELECT e
                FROM DeptEmp AS e
                WHERE e.empNo = :empNo
                ORDER BY e.fromDate DESC"""
)
@NamedQuery(
        name = "DeptEmp.selectAllFetch",
        query = """
                SELECT e
                FROM DeptEmp AS e
                JOIN FETCH e.department
                JOIN FETCH e.employee
                ORDER BY e.empNo ASC,
                         e.deptNo ASC"""
)
@NamedQuery(
        name = "DeptEmp.selectAll",
        query = """
                SELECT e
                FROM DeptEmp AS e
                ORDER BY e.empNo ASC,
                         e.deptNo ASC"""
)
@IdClass(DeptEmpId.class)
@Entity
@Table(name = DeptEmp.TABLE_NAME)
@Slf4j
public class DeptEmp
        extends _BaseEntity<DeptEmpId>
        implements _ILocalDateTerm {

    @Serial
    private static final long serialVersionUID = -6772594303267134517L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the database table to which this entity maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "dept_emp";

    // ---------------------------------------------------------------------------------------------------------- emp_no

    /**
     * The name of the table column to which the {@link DeptEmp_#empNo empNo} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_EMP_NO = Employee.COLUMN_NAME_EMP_NO;

    /**
     * The name of the entity attribute from which the {@value #COLUMN_NAME_EMP_NO} column maps. The value is {@value}.
     *
     * @see #ATTRIBUTE_NAME_EMPLOYEE
     */
    public static final String ATTRIBUTE_NAME_EMP_NO = "empNo";

    /**
     * The name of the entity attribute, of {@link Employee}, from which the {@value #COLUMN_NAME_EMP_NO} column maps.
     * The value is {@value}.
     *
     * @see #ATTRIBUTE_NAME_EMP_NO
     */
    public static final String ATTRIBUTE_NAME_EMPLOYEE = "employee";

    /**
     * A comparator compares {@value #ATTRIBUTE_NAME_EMP_NO} attribute.
     */
    private static final Comparator<DeptEmp> COMPARING_EMP_NO = Comparator.comparing(DeptEmp::getEmpNo);

    // --------------------------------------------------------------------------------------------------------- dept_no
    public static final String COLUMN_NAME_DEPT_NO = Department.COLUMN_NAME_DEPT_NO;

    public static final int COLUMN_LENGTH_DEPT_NO = Department.COLUMN_LENGTH_DEPT_NO;

    public static final String ATTRIBUTE_NAME_DEPT_NO = "deptNo";

    public static final int SIZE_MIN_DEPT_NO = Department.SIZE_MIN_DEPT_NO;

    public static final int SIZE_MAX_DEPT_NO = Department.SIZE_MAX_DEPT_NO;

    public static final String ATTRIBUTE_NAME_DEPARTMENT = "department";

    /**
     * A compartor compares {@link #ATTRIBUTE_NAME_DEPT_NO} attribute.
     */
    private static final Comparator<DeptEmp> COMPARING_DEPT_NO = Comparator.comparing(DeptEmp::getDeptNo);

    // ------------------------------------------------------------------------------------------------------- from_date
    public static final String COLUMN_NAME_FROM_DATE = _BaseEntityConstants.COLUMN_NAME_FROM_DATE;

    public static final String ATTRIBUTE_NAME_FROM_DATE = "fromDate";

    private static final Comparator<DeptEmp> COMPARING_FROM_DATE = Comparator.comparing(DeptEmp::getFromDate);

    // --------------------------------------------------------------------------------------------------------- to_date
    public static final String COLUMN_NAME_TO_DATE = _BaseEntityConstants.COLUMN_NAME_TO_DATE;

    public static final String ATTRIBUTE_NAME_TO_DATE = "toDate";

    public static final LocalDate ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED =
            _BaseEntityConstants.ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A comparator combines {@link #COMPARING_EMP_NO}, {@link #COMPARING_DEPT_NO}, and
     * {@link _ITerm#COMPARING_TERM_START}.
     */
    static final Comparator<DeptEmp> COMPARATOR =
            COMPARING_EMP_NO
                    .thenComparing(COMPARING_DEPT_NO)
                    .thenComparing(COMPARING_TERM_START);

    // ------------------------------------------------------------------------------------------ STATIC-FACTORY-METHODS
    static DeptEmp of(final Integer empNo, final String deptNo) {
        final var instance = new DeptEmp();
        instance.setEmpNo(empNo);
        instance.setDeptNo(deptNo);
        return instance;
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    protected DeptEmp() {
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
        if (!(obj instanceof DeptEmp deptEmp)) {
            return false;
        }
        if (false && !super.equals(obj)) {
            return false;
        }
        return Objects.equals(empNo, deptEmp.empNo)
                && Objects.equals(deptNo, deptEmp.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                empNo,
                deptNo
        );
    }

    // -------------------------------------------------------------------------------------------- java.lang.Comparable
    @Override
    public int compareTo(final _ITerm<?, ?> o) {
        return COMPARATOR.compare(
                this,
                (DeptEmp) Objects.requireNonNull(o, "o is null") // NullPointerException, ClassCastException
        );
    }

    // --------------------------------------------------------------------------------------------- Jakarta Persistence

    // ----------------------------------------------------------------------------------------- Jakarta Bean Validation

    /**
     * Asserts current value of {@link DeptEmp_#fromDate fromDate} attribute <em>is not after</em> current value of
     * {@link DeptEmp_#toDate toDate} attribute.
     *
     * @return {@code true} if current value of the {@link DeptEmp_#fromDate fromDate} attribute <em>is after</em>
     * current value of the {@link DeptEmp_#toDate toDate} attribute; {@code false} otherwise.
     * @deprecated covered by {@link _ITerm#isTermStartNotAfterTermEnd()}
     */
    // TODO: remove;
    @Deprecated
    @AssertFalse(message = "fromDate shouldn't be after the toDate")
    private boolean isFromDateAfterToDate() {
        if (fromDate == null || toDate == null) {
            return false;
        }
        return fromDate.isAfter(toDate);
    }

    /**
     * Asserts current value of {@link DeptEmp_#toDate toDate} attribute <em>is not after</em> the
     * {@link #ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED}.
     *
     * @return {@code true} if current value of the {@link DeptEmp_#toDate toDate} attribute <em>is after</em> the
     * {@link #ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED}; {@code false} otherwise.
     */
    @AssertFalse(message = "toDate shouldn't be after the 9999-01-01")
    private boolean isToDateAfterTheUnspecified() {
        if (toDate == null) {
            return false;
        }
        return toDate.isAfter(ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED);
    }

    // ------------------------------------------------------------------------------------------------- _ILocalDateTerm
    @Override
    public LocalDate getTermStart() {
        return getFromDate();
    }

    @Override
    public void setTermStart(final LocalDate termStart) {
        setFromDate(termStart);
    }

    @Override
    public LocalDate getTermEnd() {
        return getToDate();
    }

    @Override
    public void setTermEnd(final LocalDate termEnd) {
        setToDate(termEnd);
    }

    // ------------------------------------------------------------------------------------------------- emp_no/employee

    /**
     * Returns current value of {@link DeptEmp_#empNo empNo} attribute.
     *
     * @return current value of the {@link DeptEmp_#empNo empNo} attribute.
     */
    // TODO: narrow the scope
    public Integer getEmpNo() {
        return empNo;
    }

    /**
     * Replaces current value of {@link DeptEmp_#empNo empNo} attribute with specified value.
     *
     * @param empNo new value for the {@link DeptEmp_#empNo empNo} attribute.
     */
    // TODO: narrow the scope
    public void setEmpNo(final Integer empNo) {
        this.empNo = empNo;
    }

    /**
     * Returns current value of {@link DeptEmp_#employee employee} attribute.
     *
     * @return current value of the {@link DeptEmp_#employee employee} attribute.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Replaces current value of {@link DeptEmp_#employee employee} attribute with specified value.
     *
     * @param employee new value for the {@link DeptEmp_#employee employee} attribute.
     * @implSpec This method also replaces current value of {@link DeptEmp_#empNo empNo} attribute with
     * {@code employee?.empNo}.
     */
    public void setEmployee(final Employee employee) {
        this.employee = employee;
        setEmpNo(
                Optional.ofNullable(this.employee)
                        .map(Employee::getEmpNo)
                        .orElse(null)
        );
    }

    /**
     * Replaces current value of {@link DeptEmp_#employee employee} attribute with specified value, and returns this
     * entity.
     *
     * @param employee new value for the {@link DeptEmp_#employee employee} attribute.
     * @return this entity.
     * @implSpec Default implementation invokes {@link #setEmployee(Employee)} method with {@code employee}, and returns
     * {@code this}.
     */
    public DeptEmp employee(final Employee employee) {
        setEmployee(employee);
        return this;
    }

    // ---------------------------------------------------------------------------------------------- dept_no/department
    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(final String deptNo) {
        this.deptNo = deptNo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(final Department department) {
        this.department = department;
        setDeptNo(
                Optional.ofNullable(this.department)
                        .map(Department::getDeptNo)
                        .orElse(null)
        );
    }

    public DeptEmp department(final Department department) {
        setDepartment(department);
        return this;
    }

    // -------------------------------------------------------------------------------------------------------- fromDate

    /**
     * Returns current value of {@link DeptEmp_#fromDate fromDate} attribute.
     *
     * @return current value of {@link DeptEmp_#fromDate fromDate} attribute.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * Replaces current value of {@link DeptEmp_#fromDate fromDate} attribute with specified value.
     *
     * @param fromDate new value for the {@link DeptEmp_#fromDate fromDate} attribute.
     */
    public void setFromDate(final LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Replaces current value of {@link DeptEmp_#fromDate fromDate} attribute with specified value, and returns this
     * object.
     *
     * @param fromDate new value for the {@link DeptEmp_#fromDate fromDate} attribute.
     * @return this object.
     */
    public DeptEmp fromDate(final LocalDate fromDate) {
        setFromDate(fromDate);
        return this;
    }

    // ---------------------------------------------------------------------------------------------------------- toDate
    // TODO: javadoc
    public LocalDate getToDate() {
        return toDate;
    }

    // TODO: javadoc
    public void setToDate(final LocalDate toDate) {
        this.toDate = toDate;
    }

    // TODO: javadoc
    public DeptEmp toDate(final LocalDate toDate) {
        setToDate(toDate);
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = true, updatable = false)
    private Integer empNo;

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_DEPT_NO, max = SIZE_MAX_DEPT_NO)
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_DEPT_NO, length = COLUMN_LENGTH_DEPT_NO, nullable = false, insertable = true,
            updatable = false)
    private String deptNo;

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_DEPT_NO, nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Department department;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FROM_DATE, nullable = false, insertable = true, updatable = false)
    private LocalDate fromDate;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate toDate = ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED;
}
