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

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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
        // TODO: choose either one or another!
        if (ThreadLocalRandom.current().nextBoolean()) {
            return of(
                    Optional.ofNullable(employee).map(Employee::getEmpNo).orElse(null),
                    Optional.ofNullable(department).map(Department::getDeptNo).orElse(null)
            );
        }
        return of(null, null)
                .employee(employee)
                .department(department);
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ------------------------------------------------------------------------------------------------ java.lang.Object
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
     * Replaces current value of {@code deptNo} property with specified department's {@link Department_#deptNo deptNo}
     * attribute.
     *
     * @param department the department whose {@link Department_#deptNo deptNo} attribute is set to the {@code deptNo}
     *                   property.
     */
    public void setDepartment(final Department department) {
        setDeptNo(
                Optional.ofNullable(department).map(Department::getDeptNo).orElse(null)
        );
    }

    /**
     * Replaces current value of {@code deptNo} property with specified department's {@link Department_#deptNo deptNo}
     * attribute, and returns this object.
     *
     * @param department the department whose {@link Department_#deptNo deptNo} attribute is set to the {@code deptNo}
     *                   property.
     * @return this object.
     * @see #setDepartment(Department)
     */
    public DeptEmpId department(final Department department) {
        setDepartment(department);
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    private Integer empNo;

    @Size(min = DeptEmp.SIZE_MIN_DEPT_NO, max = DeptEmp.SIZE_MAX_DEPT_NO)
    @NotNull
    private String deptNo;
}
