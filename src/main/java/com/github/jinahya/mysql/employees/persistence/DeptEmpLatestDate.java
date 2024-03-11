package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

@NamedQuery(
        name = "DeptEmpLatestDate.selectOneByEmployee",
        query = """
                SELECT e
                FROM DeptEmpLatestDate AS e
                WHERE e.employee = :employee"""
)
@NamedQuery(
        name = "DeptEmpLatestDate.selectOneByEmpNo",
        query = """
                SELECT e
                FROM DeptEmpLatestDate AS e
                WHERE e.empNo = :empNo"""
)
@Entity
@Table(name = DeptEmpLatestDate.VIEW_NAME)
//@lombok.Setter // VIEW!
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class DeptEmpLatestDate extends _BaseEntity<Integer> {

    @Serial
    private static final long serialVersionUID = 4331323808137781879L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the view to which this entity class maps. The value is {@value}.
     */
    public static final String VIEW_NAME = "dept_emp_latest_date";

    // ---------------------------------------------------------------------------------------------------------- emp_no
    public static final String COLUMN_NAME_EMP_NO = DeptEmp.COLUMN_NAME_EMP_NO;

    // ------------------------------------------------------------------------------------------------------- from_date
    public static final String COLUMN_NAME_FROM_DATE = DeptEmp.COLUMN_NAME_FROM_DATE;

    // --------------------------------------------------------------------------------------------------------- to_date
    public static final String COLUMN_NAME_TO_DATE = DeptEmp.COLUMN_NAME_TO_DATE;

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeptEmpLatestDate that)) {
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
    @Column(name = COLUMN_NAME_EMP_NO, nullable = false,
//            insertable = false, // eclipselink
            insertable = true,
            updatable = false)
    private Integer empNo;

    @Valid
    @NotNull
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;

    // -----------------------------------------------------------------------------------------------------------------
    @Basic(optional = true)
    @Column(name = COLUMN_NAME_FROM_DATE, nullable = true, insertable = false, updatable = false)
    private LocalDate fromDate;

    @Basic(optional = true)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = true, insertable = false, updatable = false)
    private LocalDate toDate;
}
