package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

@Embeddable
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class DeptManagerId
        implements _BaseId, Comparable<DeptManagerId> {

    @Serial
    private static final long serialVersionUID = 3570976925092865329L;

    // -----------------------------------------------------------------------------------------------------------------
    static final Comparator<DeptManagerId> COMPARING_EMP_NO = Comparator.comparing(DeptManagerId::getEmpNo);

    static final Comparator<DeptManagerId> COMPARING_DEPT_NO = Comparator.comparing(DeptManagerId::getDeptNo);

    private static final Comparator<DeptManagerId> COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO =
            COMPARING_EMP_NO.thenComparing(COMPARING_DEPT_NO);

    // -----------------------------------------------------------------------------------------------------------------
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

    @Override
    public int compareTo(final DeptManagerId o) {
        return COMPARING_EMP_NO_THEN_COMPARING_DEPT_NO.compare(this, o);
    }

    // ----------------------------------------------------------------------------------------------------------- empNo

    /**
     * Replaces current value of {@link DeptManagerId_#empNo empNo} attribute with specified employee's
     * {@link Employee_#empNo empno} attribute, and returns this object.
     *
     * @param employee the employee whose {@link Employee_#empNo empNo} is set to {@link DeptManagerId_#empNo}
     *                 attribute.
     * @return this object.
     */
    public @Nonnull DeptManagerId employee(final @Nullable Employee employee) {
        setEmpNo(
                Optional.ofNullable(employee)
                        .map(Employee::getEmpNo)
                        .orElse(null)
        );
        return this;
    }

    // ---------------------------------------------------------------------------------------------------------- deptNo

    /**
     * Replaces current value of {@link DeptManagerId_#deptNo deptNo} attribute with specified department's
     * {@link Department_#deptNo deptNo} attribute, and returns this object.
     *
     * @param department the department whose {@link Department_#deptNo deptNo} is set to {@link DeptManagerId_#deptNo}
     *                   attribute.
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
