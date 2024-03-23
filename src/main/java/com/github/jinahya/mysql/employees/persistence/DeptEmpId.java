package com.github.jinahya.mysql.employees.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;

/**
 * An id class for {@link DeptEmp} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor//(access = AccessLevel.PROTECTED) // eclipselink
public class DeptEmpId
        implements _BaseId {

    @Serial
    private static final long serialVersionUID = 6356541964725322638L;

    // ------------------------------------------------------------------------------------------ STATIC-FACTORY-METHODS
    public static DeptEmpId of(final Integer empNo, String deptNo) {
        final var instance = new DeptEmpId();
        instance.setEmpNo(empNo);
        instance.setDeptNo(deptNo);
        return instance;
    }

    /**
     * Creates a new instance with specified arguments.
     *
     * @param employee   a value whose {@link Employee_#empNo empNo} is set to the {@link DeptEmp_#empNo empNo}
     *                   attribute.
     * @param department a value whose {@link Department_#deptNo deptNo} is set to the {@link DeptEmp_#deptNo deptNo}
     *                   attribute.
     * @return a new instance with {@code employee?.empNo} and {@code department?.deptNo}.
     * @see #of(Integer, String)
     */
    public static DeptEmpId from(final Employee employee, final Department department) {
        return of(null, null)
                .employee(employee)
                .department(department);
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeptEmpId that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(empNo, deptNo);
    }

    // ----------------------------------------------------------------------------------------------------------- empNo
    public void setEmployee(final Employee employee) {
        setEmpNo(
                Optional.ofNullable(employee).map(Employee::getEmpNo).orElse(null)
        );
    }

    public DeptEmpId employee(final Employee employee) {
        setEmployee(employee);
        return this;
    }

    // ---------------------------------------------------------------------------------------------------------- deptNo

    /**
     * Replaces current value of {@link DeptEmp_#deptNo deptNo} attribute with specified department's
     * {@link Department_#deptNo deptNo} attribute.
     *
     * @param department the department whose {@link Department_#deptNo deptNo} attribute is set to the
     *                   {@link DeptEmp_#deptNo deptNot} attribute.
     */
    public void setDepartment(final Department department) {
        setDeptNo(
                Optional.ofNullable(department).map(Department::getDeptNo).orElse(null)
        );
    }

    /**
     * Replaces current value of {@link DeptEmp_#deptNo deptNo} attribute with specified department's
     * {@link Department_#deptNo deptNo} attribute, and returns this object.
     *
     * @param department the department whose {@link Department_#deptNo deptNo} attribute is set to the
     *                   {@link DeptEmp_#deptNo deptNot} attribute.
     * @return this object.
     * @see #setDepartment(Department)
     */
    public DeptEmpId department(final Department department) {
        setDepartment(department);
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Integer empNo;

    private String deptNo;
}
