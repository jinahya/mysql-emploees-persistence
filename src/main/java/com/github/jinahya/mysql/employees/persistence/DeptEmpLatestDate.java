package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * An entity for {@value DeptEmpLatestDate#VIEW_NAME} view.
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
    // TODO: javadoc
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
        return Objects.hash(empNo);
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

    // TODO: remove! why?
    public void setEmpNo(final Integer empNo) {
        this.empNo = empNo;
    }

    /**
     * Returns current value of {@link DeptEmpLatestDate_#employee employee} attribute.
     *
     * @return current value of the {@link DeptEmpLatestDate_#employee employee} attribute.
     */
    public Employee getEmployee() {
        return employee;
    }

    // TODO: remove! why?
    public void setEmployee(final Employee employee) {
        this.employee = employee;
        setEmpNo(
                Optional.ofNullable(this.employee)
                        .map(Employee::getEmpNo)
                        .orElse(null)
        );
    }

    // -------------------------------------------------------------------------------------------------------- fromDate
    public LocalDate getFromDate() {
        return fromDate;
    }

    // TODO: remove! why?
    public void setFromDate(final LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    // ---------------------------------------------------------------------------------------------------------- toDate
    public LocalDate getToDate() {
        return toDate;
    }

    // TODO: remove! why?
    public void setToDate(final LocalDate toDate) {
        this.toDate = toDate;
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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;

    // -----------------------------------------------------------------------------------------------------------------
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_FROM_DATE, nullable = true, insertable = false, updatable = false)
    private LocalDate fromDate;

    @Basic(optional = true)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = true, insertable = false, updatable = false)
    private LocalDate toDate;
}
