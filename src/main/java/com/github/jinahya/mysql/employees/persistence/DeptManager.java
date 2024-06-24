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
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * An entity class maps {@value DeptManager#TABLE_NAME} table.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see DeptManagerId
 */
@NamedQuery(
        name = "DeptManager.selectAllByDepartment",
        query = """
                SELECT e
                FROM DeptManager AS e
                WHERE e.department = :department
                ORDER BY e.id.empNo ASC"""
)
@NamedQuery(
        name = "DeptManager.selectAllByIdDeptNo",
        query = """
                SELECT e
                FROM DeptManager AS e
                WHERE e.id.deptNo = :idDeptNo
                ORDER BY e.id.empNo ASC"""
)
@NamedQuery(
        name = "DeptManager.selectAllByEmployee",
        query = """
                SELECT e
                FROM DeptManager AS e
                WHERE e.employee = :employee
                ORDER BY e.id.deptNo ASC"""
)
@NamedQuery(
        name = "DeptManager.selectAllByIdEmpNo",
        query = """
                SELECT e
                FROM DeptManager AS e
                WHERE e.id.empNo = :idEmpNo
                ORDER BY e.id.deptNo ASC"""
)
@NamedQuery(
        name = "DeptManager.selectAll",
        query = """
                SELECT e
                FROM DeptManager AS e
                ORDER BY e.id.empNo ASC,
                         e.id.deptNo ASC"""
)
@Entity
@Table(name = DeptManager.TABLE_NAME)
@Setter
@Getter
public class DeptManager
        extends _BaseEntity<DeptManagerId> {

    @Serial
    private static final long serialVersionUID = 7562801904287742000L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the database table to which this entity maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "dept_manager";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EMP_NO = Employee.COLUMN_NAME_EMP_NO;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_DEPT_NO = Department.COLUMN_NAME_DEPT_NO;

    public static final int COLUMN_LENGTH_DEPT_NO = Department.COLUMN_LENGTH_DEPT_NO;

    public static final int SIZE_MIN_DEPT_NO = Department.SIZE_MIN_DEPT_NO;

    public static final int SIZE_MAX_DEPT_NO = Department.SIZE_MAX_DEPT_NO;

    // ------------------------------------------------------------------------------------------------------- from_date

    /**
     * The name of the table column to which the {@value #ATTRIBUTE_NAME_FROM_DATE} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_FROM_DATE = "from_date";

    /**
     * The name of the entity attribute from which the {@value #COLUMN_NAME_FROM_DATE} column maps. The value is
     * {@value}.
     */
    public static final String ATTRIBUTE_NAME_FROM_DATE = "fromDate";

    // --------------------------------------------------------------------------------------------------------- to_date
    public static final String COLUMN_NAME_TO_DATE = "to_date";

    public static final String ATTRIBUTE_NAME_TO_DATE = "toDate";

    public static final LocalDate COLUMN_VALUE_TO_DATE_UNSPECIFIED =
            _BaseEntityConstants.ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED;

    // ------------------------------------------------------------------------------------------ STATIC-FACTORY-METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    protected DeptManager() {
        super();
    }

    // ------------------------------------------------------------------------------------------------ java.lang.Object

    @Override
    public String toString() {
        return super.toString() + '{' +
                "id=" + id +
                ",fromDate=" + fromDate +
                ",toDate=" + toDate +
                '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeptManager that)) {
            return false;
        }
        if (false && !super.equals(obj)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                id
        );
    }

    // --------------------------------------------------------------------------------------------- Jakarta Persistence

    // ----------------------------------------------------------------------------------------- Jakarta Bean Validation

    // ------------------------------------------------------------------------------------------------- _ILocalDateTerm
    // TODO: implement methods

    // ------------------------------------------------------------------------------------------ id/employee/department

    /**
     * Returns current value of {@link DeptManager_#id id} attribute.
     *
     * @return current value of the {@link DeptManager_#id id} attribute.
     */
    // TODO: narrow the scope
    public DeptManagerId getId() {
        return id;
    }

    /**
     * Replaces current value of {@link DeptManager_#id id} attribute with specified value.
     *
     * @param id new value for the {@link DeptManager_#id id} attribute.
     */
    // TODO: narrow the scope
    public void setId(final DeptManagerId id) {
        this.id = id;
    }

    /**
     * Returns current value of {@link DeptManager_#employee employee} attribute.
     *
     * @return current value of the {@link DeptManager_#employee employee} attribute.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Replaces current value of {@link DeptManager_#employee employee} attribute with specified value.
     *
     * @param employee new value for the {@link DeptManager_#employee employee} attribute.
     * @implSpec This method also updates {@link DeptManagerId_#empNo id.empNo} attribute path with
     * {@code employee?.empNo}.
     */
    public void setEmployee(final Employee employee) {
        this.employee = employee;
        id.setEmpNo(
                Optional.ofNullable(this.employee)
                        .map(Employee::getEmpNo)
                        .orElse(null)
        );
    }

    /**
     * Replaces current value of {@link DeptManager_#employee employee} attribute with specified value, and returns this
     * object.
     *
     * @param employee new value for the {@link DeptManager_#employee employee} attribute.
     * @return this object.
     * @see #setEmployee(Employee)
     */
    public DeptManager employee(final Employee employee) {
        setEmployee(employee);
        return this;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(final Department department) {
        this.department = department;
        id.setDeptNo(
                Optional.ofNullable(department)
                        .map(Department::getDeptNo)
                        .orElse(null)
        );
    }

    public DeptManager department(final Department department) {
        setDepartment(department);
        return this;
    }

    // -------------------------------------------------------------------------------------------------------- fromDate

    /**
     * Returns current value of {@link DeptManager_#fromDate fromDate} attribute.
     *
     * @return current value of {@link DeptManager_#fromDate fromDate} attribute.
     */
    public @NotNull LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * Replaces current value of {@link DeptManager_#fromDate fromDate} attribute with specified value.
     *
     * @param fromDate new value for the {@link DeptManager_#fromDate fromDate} attribute.
     */
    public void setFromDate(@NotNull LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    // ---------------------------------------------------------------------------------------------------------- toDate
    public @NotNull LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(@NotNull LocalDate toDate) {
        this.toDate = toDate;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Valid
    @NotNull
    @EmbeddedId
    private /* final */ DeptManagerId id = new DeptManagerId();

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_DEPT_NO, nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Department department;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FROM_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate fromDate;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate toDate;
}
