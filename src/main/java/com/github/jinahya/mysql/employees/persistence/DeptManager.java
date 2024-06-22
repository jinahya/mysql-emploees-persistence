package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

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

    public static final LocalDate COLUMN_VALUE_TO_DATE_UNSPECIFIED = _BaseEntityConstants.ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED;

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
        if (!super.equals(obj)) {
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

    // -------------------------------------------------------------------------------------------------------------- id
    public <R> R applyId(final Function<? super DeptManagerId, ? extends R> function) {
        return Objects.requireNonNull(function, "function is null").apply(getId());
    }

    public DeptManager acceptId(final Consumer<? super DeptManagerId> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");
        return applyId(v -> {
            consumer.accept(v);
            return this;
        });
    }

    // -------------------------------------------------------------------------------------------------------- employee

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(final Employee employee) {
        this.employee = employee;
        id.setEmpNo(
                Optional.ofNullable(this.employee)
                        .map(Employee::getEmpNo)
                        .orElse(null)
        );
    }

    // ------------------------------------------------------------------------------------------------------ department
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

    // -----------------------------------------------------------------------------------------------------------------
    @Valid
    @NotNull
    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private DeptManagerId id = new DeptManagerId();

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
