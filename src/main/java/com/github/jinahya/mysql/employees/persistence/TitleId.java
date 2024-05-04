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
import java.time.LocalDate;
import java.util.Objects;

/**
 * An id class for {@link Title} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Embeddable
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class TitleId
        implements _BaseId {

    @Serial
    private static final long serialVersionUID = -7452067856104640617L;

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TitleId that)) {
            return false;
        }
        return Objects.equals(empNo, that.empNo) &&
                Objects.equals(title, that.title) &&
                Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, title, fromDate);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = Title.COLUMN_NAME_EMP_NO, nullable = false, insertable = true, updatable = false)
    private Integer empNo;

    @NotNull
    @Basic(optional = false)
    @Column(name = Title.COLUMN_NAME_TITLE, nullable = false, insertable = true, updatable = false)
    private String title;

    @NotNull
    @Basic(optional = false)
    @Column(name = Title.COLUMN_NAME_FROM_DATE, nullable = false, insertable = true, updatable = false)
    private LocalDate fromDate;

}
