package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@NamedQuery(
        name = "Title.selectAllByEmployee",
        query = """
                SELECT e
                FROM Title AS e
                WHERE e.employee = :employee
                ORDER BY e.id.title ASC, e.id.fromDate ASC"""
)
@NamedQuery(
        name = "Title.selectAllByIdEmpNo",
        query = """
                SELECT e
                FROM Title AS e
                WHERE e.id.empNo = :idEmpNo
                ORDER BY e.id.title ASC, e.id.fromDate ASC"""
)
@NamedQuery(
        name = "Title.selectAll",
        query = """
                SELECT e
                FROM Title AS e
                ORDER BY e.id.empNo ASC, e.id.title ASC, e.id.fromDate ASC"""
)
@Entity
@Table(name = Title.TABLE_NAME)
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Title extends _BaseEntity<TitleId> {

    @Serial
    private static final long serialVersionUID = -6271293641555396755L;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "titles";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EMP_NO = Employee.COLUMN_NAME_EMP_NO;

    // ---------------------------------------------------------------------------------------------------------- salary
    public static final String COLUMN_NAME_TITLE = "title";

    // ------------------------------------------------------------------------------------------------------- from_date
    public static final String COLUMN_NAME_FROM_DATE = "from_date";

    // --------------------------------------------------------------------------------------------------------- to_date
    public static final String COLUMN_NAME_TO_DATE = "to_date";

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Title that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // -------------------------------------------------------------------------------------------------------- employee

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(final Employee employee) {
        this.employee = employee;
        id.setEmpNo(
                Optional.ofNullable(this.employee)
                        .map(Employee::getEmpNo)
                        .orElse(null)
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Valid
    @NotNull
    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private TitleId id = new TitleId();

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate toDate;
}
