package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptEmp
        extends _BaseEntity<DeptEmpId> {

    @Serial
    private static final long serialVersionUID = -6772594303267134517L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the database table to which this entity maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "dept_emp";

    // ---------------------------------------------------------------------------------------------------------- emp_no
    public static final String COLUMN_NAME_EMP_NO = Employee.COLUMN_NAME_EMP_NO;

    public static final String ATTRIBUTE_NAME_EMP_NO = "empNo";

    public static final String ATTRIBUTE_NAME_EMPLOYEE = "employee";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_DEPT_NO = Department.COLUMN_NAME_DEPT_NO;

    public static final int COLUMN_LENGTH_DEPT_NO = Department.COLUMN_LENGTH_DEPT_NO;

    public static final String ATTRIBUTE_NAME_DEPT_NO = "deptNo";

    public static final int SIZE_MIN_DEPT_NO = Department.SIZE_MIN_DEPT_NO;

    public static final int SIZE_MAX_DEPT_NO = Department.SIZE_MAX_DEPT_NO;

    public static final String ATTRIBUTE_NAME_DEPARTMENT = "department";

    // ------------------------------------------------------------------------------------------------------- from_date
    public static final String COLUMN_NAME_FROM_DATE = _DomainConstants.COLUMN_NAME_FROM_DATE;

    public static final String ATTRIBUTE_NAME_FROM_DATE = "fromDate";

    public static final Comparator<DeptEmp> COMPARING_FROM_DATE = Comparator.comparing(DeptEmp::getFromDate);

    // --------------------------------------------------------------------------------------------------------- to_date
    public static final String COLUMN_NAME_TO_DATE = _DomainConstants.COLUMN_NAME_TO_DATE;

    public static final String ATTRIBUTE_NAME_TO_DATE = "toDate";

    public static final LocalDate ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED =
            _DomainConstants.ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED;

    // ------------------------------------------------------------------------------------------ STATIC FACTORY METHODS
    static DeptEmp of(final Integer empNo, final String deptNo) {
        final var instance = new DeptEmp();
        instance.setEmpNo(empNo);
        instance.setDeptNo(deptNo);
        return instance;
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ------------------------------------------------------------------------------------------------ java.lang.Object

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeptEmp that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }

    // --------------------------------------------------------------------------------------------- Jakarta Persistence
    @jakarta.persistence.PrePersist
    private void onPrePersist() {
        if (toDate == null) {
            toDate = ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED;
        }
    }

    // ----------------------------------------------------------------------------------------- Jakarta Bean Validation

    /**
     * Asserts current value of {@link DeptEmp_#fromDate fromDate} attribute <em>is not after</em> current value of
     * {@link DeptEmp_#toDate toDate} attribute.
     *
     * @return {@code true} if current value of the {@link DeptEmp_#fromDate fromDate} attribute <em>is after</em>
     * current value of the {@link DeptEmp_#toDate toDate} attribute; {@code false} otherwise.
     */
//    @jakarta.validation.constraints.AssertFalse(message = "fromDate shouldn't be after the toDate")
    private boolean isFromDateAfterToDate() {
        if (fromDate == null || toDate == null) {
            return false;
        }
        return fromDate.isAfter(toDate);
    }

    /**
     * Asserts current value of {@link DeptEmp_#toDate toDate} attribute <em>is not after</em>
     * {@link #ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED}.
     *
     * @return {@code true} if current value of the {@link DeptEmp_#toDate toDate} attribute <em>is after</em> the
     * {@link #ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED}; {@code false} otherwise.
     */
    //    @jakarta.validation.constraints.AssertFalse
    private boolean isToDateAfterTheUnspecified() {
        if (toDate == null) {
            return false;
        }
        return toDate.isAfter(ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED);
    }

    // ------------------------------------------------------------------------------------------------- emp_no/employee

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

    // ---------------------------------------------------------------------------------------------- dept_no/department
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

    // -------------------------------------------------------------------------------------------------------- fromDate

    // ---------------------------------------------------------------------------------------------------------- toDate

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = true, updatable = false)
//    @lombok.Setter(AccessLevel.PACKAGE)
//    @lombok.Getter(AccessLevel.PACKAGE)
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
//    @lombok.Setter(AccessLevel.PACKAGE)
//    @lombok.Getter(AccessLevel.PACKAGE)
    private String deptNo;

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_DEPT_NO, nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Department department;

    // -----------------------------------------------------------------------------------------------------------------
//    @jakarta.validation.constraints.PastOrPresent
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FROM_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate fromDate;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate toDate;
}
