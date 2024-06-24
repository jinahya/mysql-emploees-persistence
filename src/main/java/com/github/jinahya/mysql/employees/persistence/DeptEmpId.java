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
import lombok.Setter;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * An id class for {@link DeptEmp} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Setter
@Getter
public class DeptEmpId
        implements _BaseId {

    @Serial
    private static final long serialVersionUID = 6356541964725322638L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    /**
     * Creates a new instance, of {@link T}, with specified values.
     *
     * @param instanceSupplier a supplier for a new instance.
     * @param empNo            a value for {@code empNo} property.
     * @param deptNo           a value for {@code deptNo} property.
     * @param <T>              instance type parameter
     * @return a new instance, of {@code idClass}, with {@code empNo} and {@code deptNo}.
     * @see #newInstanceOf(Supplier, Integer, String)
     */
    static <T extends DeptEmpId> T newInstanceOf(final Supplier<? extends T> instanceSupplier, final Integer empNo,
                                                 String deptNo) {
        Objects.requireNonNull(instanceSupplier, "instanceSupplier is null");
        final var instance = instanceSupplier.get();
        instance.setEmpNo(empNo);
        instance.setDeptNo(deptNo);
        return instance;
    }

    /**
     * Creates a new instance, of specified type, with specified values.
     *
     * @param idClass the type to be initialized.
     * @param empNo   a value for {@code empNo} property.
     * @param deptNo  a value for {@code deptNo} property.
     * @param <T>     instance type parameter
     * @return a new instance, of {@code idClass}, with {@code empNo} and {@code deptNo}.
     * @see #newInstanceOf(Supplier, Integer, String)
     */
    @SuppressWarnings({
            "java:S112", // new RuntimeException
            "java:S3011" // setAccessible
    })
    static <T extends DeptEmpId> T newInstanceOf(final Class<T> idClass, final Integer empNo, String deptNo) {
        return newInstanceOf(
                () -> {
                    try {
                        final var constructor = idClass.getDeclaredConstructor();
                        if (!constructor.canAccess(null)) {
                            constructor.setAccessible(true);
                        }
                        return constructor.newInstance();
                    } catch (final ReflectiveOperationException roe) {
                        throw new RuntimeException("failed to create a new instance of " + idClass, roe);
                    }
                },
                empNo,
                deptNo
        );
    }

    /**
     * Returns a new instance of specified values
     *
     * @param empNo  a value for {@code empNo} property.
     * @param deptNo a value for {@code deptNo} property.
     * @return a new instance of {@code empNo} and {@code deptNo}.
     * @see #newInstanceOf(Class, Integer, String)
     */
    public static DeptEmpId of(final Integer empNo, String deptNo) {
        return newInstanceOf(
                DeptEmpId::new,
                empNo,
                deptNo
        );
    }

    /**
     * Creates a new instance with specified arguments.
     *
     * @param employee   a value whose {@link Employee_#empNo empNo} is set to the {@code empNo} property.
     * @param department a value whose {@link Department_#deptNo deptNo} is set to the {@code deptNo} property.
     * @return a new instance with {@link Employee#getEmpNo() employee?.empNo} and
     * {@link Department#getDeptNo() department?.deptNo}.
     * @see #of(Integer, String)
     */
    public static DeptEmpId from(final Employee employee, final Department department) {
        return of(
                Optional.ofNullable(employee).map(Employee::getEmpNo).orElse(null),
                Optional.ofNullable(department).map(Department::getDeptNo).orElse(null)
        );
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    protected DeptEmpId() {
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

    /**
     * Returns current value of {@code empNo} property.
     *
     * @return current value of the {@code empNo} property.
     */
    public Integer getEmpNo() {
        return empNo;
    }

    // ---------------------------------------------------------------------------------------------------------- deptNo
    public String getDeptNo() {
        return deptNo;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    private Integer empNo;

    @Size(min = DeptEmp.SIZE_MIN_DEPT_NO, max = DeptEmp.SIZE_MAX_DEPT_NO)
    @NotNull
    private String deptNo;
}
