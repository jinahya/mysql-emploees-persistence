package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class DeptManagerId implements Serializable {

    @Serial
    private static final long serialVersionUID = 3570976925092865329L;

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
