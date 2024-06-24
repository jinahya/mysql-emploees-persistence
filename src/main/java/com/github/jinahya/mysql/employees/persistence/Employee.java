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

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * An entity class maps {@value Employee#TABLE_NAME} table.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a href="https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2-m1#a5538">4.8.5.
 * Aggregate Functions in the SELECT Clause</a>
 */
@NamedQuery(
        name = "Employee.selectHireYearsAndCounts",
        query = """
                SELECT e.gender, COUNT(e.gender) AS c
                FROM Employee AS e
                GROUP BY e.gender
                ORDER BY c DESC"""
)
@NamedQuery(
        name = "Employee.selectGendersAndCounts",
        query = """
                SELECT e.gender, COUNT(e.gender) AS c
                FROM Employee AS e
                GROUP BY e.gender
                ORDER BY e.gender"""
)
@NamedQuery(
        name = "Employee.selectFirstNamesAndCounts",
        query = """
                SELECT e.firstName, COUNT(e.firstName) AS c
                FROM Employee AS e
                GROUP BY e.firstName
                ORDER BY c DESC"""
)
@NamedQuery(
        name = "Employee.selectDistinctBirthYear",
        query = """
                SELECT DISTINCT EXTRACT(YEAR FROM e.birthDate) AS birthYear
                FROM Employee AS e
                ORDER BY birthYear"""
)
@NamedQuery(
        name = "Employee.selectMinMaxBirthDate",
        query = """
                SELECT MIN(e.birthDate), MAX(e.birthDate)
                FROM Employee AS e"""
)
@NamedQuery(
        name = "Employee.selectMaxEmpNo",
        query = """
                SELECT MAX(e.empNo)
                FROM Employee AS e"""
)
@NamedQuery(
        name = "Employee.selectAll",
        query = """
                SELECT e
                FROM Employee AS e
                ORDER BY e.empNo ASC"""
)
@Entity
@Table(name = Employee.TABLE_NAME)
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Employee
        extends _BaseEntity<Integer> {

    @Serial
    private static final long serialVersionUID = -6603383818841085999L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the database table to which this entity class maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "employees";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the table column to which the {@link Employee_#empNo empNo} attribute maps. The value is {@value}.
     */
    public static final String COLUMN_NAME_EMP_NO = "emp_no";

    // ------------------------------------------------------------------------------------------------------ birth_date
    public static final String COLUMN_NAME_BIRTH_DATE = "birth_date";

    // ------------------------------------------------------------------------------------------------------ first_name
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    /**
     * The length of the {@value #COLUMN_NAME_FIRST_NAME} column. The value is {@value}.
     */
    public static final int COLUMN_LENGTH_FIRST_NAME = 14;

    public static final int SIZE_MAX_FIRST_NAME = COLUMN_LENGTH_FIRST_NAME;

    // ------------------------------------------------------------------------------------------------------- last_name
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    public static final int COLUMN_LENGTH_LAST_NAME = 16;

    // ---------------------------------------------------------------------------------------------------------- gender
    public static final String COLUMN_NAME_GENDER = "gender";

    /**
     * The column value of {@value} for the {@value #COLUMN_NAME_GENDER} column.
     */
    public static final String COLUMN_VALUE_GENDER_F = "F";

    public static final String COLUMN_VALUE_GENDER_M = "M";

    /**
     * Constants for {@link Employee_#gender gender} attribute.
     *
     * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
     */
    public enum Gender {

        /**
         * Constants for {@value #COLUMN_VALUE_GENDER_F} column value.
         */
        F(COLUMN_VALUE_GENDER_F),

        /**
         * Constants for {@value #COLUMN_VALUE_GENDER_M} column value.
         */
        M(COLUMN_VALUE_GENDER_M);

        /**
         * Finds the value whose {@link #getColumnValue() columnValue} matches specified value.
         *
         * @param columnValue the value of {@link #getColumnValue() columnValue} to match.
         * @return the value whose {@link #getColumnValue() columnValue} matches {@code columnValue}.
         * @throws IllegalArgumentException when no value matches.
         */
        public static Gender valueOfColumnValue(final String columnValue) {
            for (final var value : values()) {
                if (Objects.equals(value.columnValue, columnValue)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("no value for the columnValue: " + columnValue);
        }

        Gender(final String columnValue) {
            this.columnValue = Objects.requireNonNull(columnValue, "columnValue is null");
        }

        public String getColumnValue() {
            return columnValue;
        }

        private final String columnValue;
    }

    /**
     * An attribute converter for {@link Employee_#gender} attribute.
     *
     * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
     */
    @Converter(autoApply = false)
    public static class GenderConverter
            implements AttributeConverter<Gender, String> {

        @Override
        public String convertToDatabaseColumn(final Gender attribute) {
            if (attribute == null) {
                return null;
            }
            return attribute.getColumnValue();
        }

        @Override
        public Gender convertToEntityAttribute(final String dbData) {
            if (dbData == null) {
                return null;
            }
            return Gender.valueOfColumnValue(dbData); // may throw an instance of IllegalArgumentException
        }
    }

    // ------------------------------------------------------------------------------------------ STATIC-FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ------------------------------------------------------------------------------------------------------- hire_date

    /**
     * The name of the table column to which the {@link Employee_#hireDate hireDate} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_HIRE_DATE = "hire_date";

    // ------------------------------------------------------------------------------------------ STATIC FACTORY METHODS
    static Employee of(final Integer empNo) {
        final Employee instance = new Employee();
        instance.setEmpNo(empNo);
        return instance;
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ------------------------------------------------------------------------------------------------ java.lang.Object

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Employee that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo);
    }

    // ----------------------------------------------------------------------------------------------------------- empNo

    // ------------------------------------------------------------------------------------------------------- birthDate

    /**
     * Applies current value of {@link Employee_#birthDate birthDate} attribute to specified function, and returns the
     * result.
     *
     * @param function the function to be applied with the current value of the {@link Employee_#birthDate birthDate}
     *                 attribute.
     * @param <R>      result type parameter
     * @return the result of the {@code function}; {@code null} when current value of the
     * {@link Employee_#birthDate birthDate} attribute is {@code null}.
     */
    public <R> @Nullable R applyBirthDate(final @NonNull Function<? super LocalDate, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return Optional.ofNullable(getBirthDate())
                       .map(function)
                       .orElse(null);
    }

    /**
     * Returns the {@link Year#from(TemporalAccessor) year} of current value of {@link Employee_#birthDate birthDate}
     * attribute.
     *
     * @return the {@link Year#from(TemporalAccessor) year} of current value of {@link Employee_#birthDate birthDate}
     * attribute; {@code null} when current value of {@link Employee_#birthDate birthDate} attribute is {@code null}.
     * @see #applyBirthDate(Function)
     * @see Year#from(TemporalAccessor)
     */
    public @Nullable Year getBirthYear() {
        return applyBirthDate(Year::from);
    }

    public @Nullable YearMonth getBirthYearMonth() {
        return applyBirthDate(YearMonth::from);
    }

    public @Nullable Month getBirthMonth() {
        return applyBirthDate(Month::from);
    }

    public @Nullable MonthDay getBirthMonthDay() {
        return applyBirthDate(MonthDay::from);
    }

    public @Nullable DayOfWeek getBirthDayOfWeek() {
        return applyBirthDate(DayOfWeek::from);
    }

    // ------------------------------------------------------------------------------------------------------- firstName

    // -------------------------------------------------------------------------------------------------------- lastName

    // -------------------------------------------------------------------------------------------------------- hireDate
    public <R> R applyHireDate(final @NonNull Function<? super LocalDate, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return Optional.ofNullable(getHireDate())
                       .map(function)
                       .orElse(null);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = true, updatable = false)
    private Integer empNo;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_BIRTH_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate birthDate;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false, insertable = true, updatable = true,
            length = COLUMN_LENGTH_FIRST_NAME)
    private String firstName;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false, insertable = true, updatable = true,
            length = COLUMN_LENGTH_LAST_NAME)
    private String lastName;

    @NotNull
//    @Convert(converter = GenderConverter.class) // TODO: uncomment
    @Enumerated(EnumType.STRING)                  // TODO: comment-out
    @Column(name = COLUMN_NAME_GENDER, nullable = false, insertable = true, updatable = true)
    private Gender gender;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_HIRE_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate hireDate;

    // -------------------------------------------------------------------------------------------------------- dept_emp
    @OrderBy(DeptEmp.ATTRIBUTE_NAME_DEPT_NO + " ASC")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = DeptEmp.TABLE_NAME,
               joinColumns = {
                       @JoinColumn(name = DeptEmp.COLUMN_NAME_EMP_NO, referencedColumnName = COLUMN_NAME_EMP_NO)
               },
               inverseJoinColumns = {
                       @JoinColumn(name = DeptEmp.COLUMN_NAME_DEPT_NO,
                                   referencedColumnName = Department.COLUMN_NAME_DEPT_NO)
               }
    )
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<@Valid @NotNull Department> departments;

    // ---------------------------------------------------------------------------------------------------------- salary
    @OrderBy(Salary.ATTRIBUTE_NAME_FROM_DATE + " ASC")
    @OneToMany(mappedBy = Salary.ATTRIBUTE_NAME_EMPLOYEE, fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<@Valid @NotNull Salary> salaries;
}
