package com.github.jinahya.mysql.employees.persistence;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class SalaryId implements _BaseId {

    @Serial
    private static final long serialVersionUID = -378954798191441067L;

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SalaryId that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(empNo, fromDate);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    private Integer empNo;

    @NotNull
    private LocalDate fromDate;
}
