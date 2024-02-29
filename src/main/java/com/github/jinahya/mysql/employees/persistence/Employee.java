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

    // ------------------------------------------------------------------------------------------------------- last_name
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    // ---------------------------------------------------------------------------------------------------------- gender
    public static final String COLUMN_NAME_GENDER = "gender";

    public static final String COLUMN_VALUE_GENER_F = "F";

    public static final String COLUMN_VALUE_GENER_M = "M";

    public enum Gender {
        F(COLUMN_VALUE_GENER_F),
        M(COLUMN_VALUE_GENER_M);

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

    @Converter(autoApply = true)
    public static class GenerConverter implements AttributeConverter<Gender, String> {

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
            return Gender.valueOfColumnValue(dbData);
        }
    }

    // ------------------------------------------------------------------------------------------------------- hire_date
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

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = true, updatable = false)
    private Integer empNo;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_BIRTH_DATE, nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false)
    private String firstName;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_NAME_GENDER, nullable = false)
    private Gender gender;

    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_HIRE_DATE, nullable = false)
    private LocalDate hireDate;
}
