package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

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
        query = "SELECT MIN(e.birthDate), MAX(e.birthDate) FROM Employee AS e"
)
@NamedQuery(
        name = "Employee.selectMaxEmpNo",
        query = "SELECT MAX(e.empNo) FROM Employee AS e"
)
@NamedQuery(
        name = "Employee.selectAll",
        query = "SELECT e FROM Employee AS e"
)
@Entity
@Table(name = Employee.TABLE_NAME)
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Employee extends BaseEntity<Integer> {

    @Serial
    private static final long serialVersionUID = -6603383818841085999L;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "employees";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EMP_NO = "emp_no";

    // ------------------------------------------------------------------------------------------------------ birth_date
    public static final String COLUMN_NAME_BIRTH_DATE = "birth_date";

    // ------------------------------------------------------------------------------------------------------ first_name
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    public static final int COLUMN_LENGTH_FIRST_NAME = 14;

    // ------------------------------------------------------------------------------------------------------- last_name
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    public static final int COLUMN_LENGTH_LAST_NAME = 16;

    // ---------------------------------------------------------------------------------------------------------- gender
    public static final String COLUMN_NAME_GENDER = "gender";

    /**
     * The column value of {@value} for the {@value #COLUMN_NAME_GENDER} column.
     */
    public static final String COLUMN_VALUE_GENER_F = "F";

    public static final String COLUMN_VALUE_GENER_M = "M";

    /**
     * Constants for {@link Employee_#gender gender} attribute.
     *
     * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
     */
    public enum Gender {

        /**
         * Constants for {@value #COLUMN_VALUE_GENER_F} column value.
         */
        F(COLUMN_VALUE_GENER_F),

        /**
         * Constants for {@value #COLUMN_VALUE_GENER_M} column value.
         */
        M(COLUMN_VALUE_GENER_M);

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
    public static class GenderConverter implements AttributeConverter<Gender, String> {

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

    // ------------------------------------------------------------------------------------------------------- hire_date

    /**
     * The name of the table column to which the {@link Employee_#hireDate hireDate} attribute maps. The value is
     * {@value}.
     */
    public static final String COLUMN_NAME_HIRE_DATE = "hire_date";

    // -----------------------------------------------------------------------------------------------------------------

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

    // ------------------------------------------------------------------------------------------------------- firstName

    // -------------------------------------------------------------------------------------------------------- lastName

    // -------------------------------------------------------------------------------------------------------- hireDate

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
    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_NAME_GENDER, nullable = false, insertable = true, updatable = true)
    private Gender gender;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_HIRE_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate hireDate;
}
