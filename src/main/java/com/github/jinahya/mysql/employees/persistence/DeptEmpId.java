package com.github.jinahya.mysql.employees.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Objects;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor//(access = AccessLevel.PROTECTED) // eclipselink
public class DeptEmpId implements BaseId {

    @Serial
    private static final long serialVersionUID = 6356541964725322638L;

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

    // -----------------------------------------------------------------------------------------------------------------
    private Integer empNo;

    private String deptNo;
}
