package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An embeddable id class for {@link DeptManager} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Embeddable
@Slf4j
public class DeptManagerId
        implements _BaseId,
                   Comparable<DeptManagerId> {

    @Serial
    private static final long serialVersionUID = 3570976925092865329L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * A comparator compares {@link DeptManagerId_#empNo empNo} attribute.
     */
    public static final Comparator<DeptManagerId> COMPARING_EMP_NO = Comparator.comparing(DeptManagerId::getEmpNo);

    // TODO: javadoc
    public static final Comparator<DeptManagerId> COMPARING_DEPT_NO = Comparator.comparing(DeptManagerId::getDeptNo);

    // TODO: javadoc
    private static final Comparator<DeptManagerId> COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO =
            COMPARING_EMP_NO.thenComparing(COMPARING_DEPT_NO);

    // ------------------------------------------------------------------------------------------ STATIC-FACTORY-METHODS

    /**
     * Creates a new instance of specified value.
     *
     * @param empNo  a value for the {@link DeptManagerId_#empNo} attribute.
     * @param deptNo a value for {@link DeptManagerId_#deptNo} attribute.
     * @return a new attribute of {@code empNo} and {@code deptNo}.
     */
    public static DeptManagerId of(final Integer empNo, final String deptNo) {
        final var instance = new DeptManagerId();
        instance.setEmpNo(empNo);
        instance.setDeptNo(deptNo);
        return instance;
    }

    /**
     * Creates a new instance from specified values.
     *
     * @param employee   an employee for {@link DeptManagerId_#empNo empNo} attribute.
     * @param department a department for {@link DeptManagerId_#deptNo deptNo} attribute.
     * @return a new instance of {@code employee.empNo} and {@code department.deptNo}.
     */
    public static DeptManagerId from(final Employee employee, final Department department) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return of(
                    Optional.ofNullable(employee).map(Employee::getEmpNo).orElse(null),
                    Optional.ofNullable(department).map(Department::getDeptNo).orElse(null)
            );
        }
        return new DeptManagerId()
                .employee(employee)
                .department(department);
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    public DeptManagerId() {
        super();
    }

    // ------------------------------------------------------------------------------------------------ java.lang.Object
    @Override
    public String toString() {
        return super.toString() + '{' +
                "empNo=" + empNo +
                ",deptNo=" + deptNo +
                '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeptManagerId that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, deptNo);
    }

    // -------------------------------------------------------------------------------------------- java.lang.Comparable
    @Override
    public int compareTo(final DeptManagerId o) {
        return COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO.compare(this, o);
    }

    // ----------------------------------------------------------------------------------------------------------- empNo

    /**
     * Returns current value of {@link DeptManagerId_#empNo empNo} attribute.
     *
     * @return current value of the {@link DeptManagerId_#empNo empNo} attribute.
     */
    public Integer getEmpNo() {
        return empNo;
    }

    /**
     * Replaces current value of {@link DeptManagerId_#empNo empNo} attribute with specified value.
     *
     * @param empNo new value for the {@link DeptManagerId_#empNo empNo} attribute.
     */
    public void setEmpNo(final Integer empNo) {
        this.empNo = empNo;
    }

    /**
     * Replaces current value of {@link DeptManagerId_#empNo empNo} attribute with specified employee's
     * {@link Employee_#empNo empNo} attribute, and returns this object.
     *
     * @param employee the employee whose {@link Employee_#empNo empNo} is set to {@link DeptManagerId_#empNo}
     *                 attribute; may be {@code null}.
     * @return this object.
     */
    public DeptManagerId employee(final Employee employee) {
        setEmpNo(
                Optional.ofNullable(employee)
                        .map(Employee::getEmpNo)
                        .orElse(null)
        );
        return this;
    }

    // ---------------------------------------------------------------------------------------------------------- deptNo
    // TODO: javadoc
    public String getDeptNo() {
        return deptNo;
    }

    // TODO: javadoc
    public void setDeptNo(final String deptNo) {
        this.deptNo = deptNo;
    }

    /**
     * Replaces current value of {@link DeptManagerId_#deptNo deptNo} attribute with specified department's
     * {@link Department_#deptNo deptNo} attribute, and returns this object.
     *
     * @param department the department whose {@link Department_#deptNo deptNo} is set to {@link DeptManagerId_#deptNo}
     *                   attribute; may be {@code null}.
     * @return this object.
     */
    public DeptManagerId department(final Department department) {
        setDeptNo(
                Optional.ofNullable(department)
                        .map(Department::getDeptNo)
                        .orElse(null)
        );
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = DeptManager.COLUMN_NAME_EMP_NO, nullable = false, insertable = true, updatable = false)
    private Integer empNo;

    @NotNull
    @Basic(optional = false)
    @Column(name = DeptManager.COLUMN_NAME_DEPT_NO, nullable = false, insertable = true, updatable = false)
    private String deptNo;
}
