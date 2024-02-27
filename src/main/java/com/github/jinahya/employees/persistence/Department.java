package com.github.jinahya.employees.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Objects;

@Entity
@Table(name = Department.TABLE_NAME)
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Department extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3430343752363795141L;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "departments";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_DEPT_NO = "dept_no";

    public static final int COLUMN_LENGTH_DEPT_NO = 4;

    public static final int SIZE_MIN_DEPT_NO = COLUMN_LENGTH_DEPT_NO;

    public static final int SIZE_MAX_DEPT_NO = COLUMN_LENGTH_DEPT_NO;

    // ------------------------------------------------------------------------------------------------------ first_name
    public static final String COLUMN_NAME_DEPT_NAME = "dept_name";

    public static final int COLUMN_LENGTH_DEPT_NAME = 40;

    public static final int SIZE_MIN_DEPT_NAME = 0;

    public static final int SIZE_MAX_DEPT_NAME = COLUMN_LENGTH_DEPT_NAME;

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Department that)) {
            return false;
        }
        return Objects.equals(deptNo, that.deptNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptNo);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(min = SIZE_MIN_DEPT_NO, max = SIZE_MAX_DEPT_NO)
    @NotNull
    @Id
    @Column(name = COLUMN_NAME_DEPT_NO, length = COLUMN_LENGTH_DEPT_NO, nullable = false, insertable = true,
            updatable = false)
    private String deptNo;

    @Size(min = SIZE_MIN_DEPT_NAME, max = SIZE_MAX_DEPT_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_DEPT_NAME, length = COLUMN_LENGTH_DEPT_NAME, nullable = false)
    private String deptName;
}
