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

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * An entity class maps {@value Title#TABLE_NAME} table.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see TitleId
 */
@NamedQuery(
        name = "Title.selectAllByEmployeeAndIdTitle",
        query = """
                SELECT e
                FROM Title AS e
                WHERE e.employee = :employee
                  AND e.id.title = :idTitle
                ORDER BY e.id.fromDate ASC"""
)
@NamedQuery(
        name = "Title.selectAllByIdEmpNoAndIdTitle",
        query = """
                SELECT e
                FROM Title AS e
                WHERE e.id.empNo = :idEmpNo
                  AND e.id.title = :idTitle
                ORDER BY e.id.fromDate ASC"""
)
@NamedQuery(
        name = "Title.selectAllByEmployee",
        query = """
                SELECT e
                FROM Title AS e
                WHERE e.employee = :employee
                ORDER BY e.id.title ASC,
                         e.id.fromDate ASC"""
)
@NamedQuery(
        name = "Title.selectAllByIdEmpNo",
        query = """
                SELECT e
                FROM Title AS e
                WHERE e.id.empNo = :idEmpNo
                ORDER BY e.id.title ASC,
                         e.id.fromDate ASC"""
)
@NamedQuery(
        name = "Title.selectAll",
        query = """
                SELECT e
                FROM Title AS e
                ORDER BY e.id.empNo ASC,
                         e.id.title ASC,
                         e.id.fromDate ASC"""
)
@Entity
@Table(name = Title.TABLE_NAME)
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Title
        extends _BaseEntity<TitleId> {

    @Serial
    private static final long serialVersionUID = -6271293641555396755L;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The name of the database table to which this entity maps. The value is {@value}.
     */
    public static final String TABLE_NAME = "titles";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EMP_NO = Employee.COLUMN_NAME_EMP_NO;

    // ---------------------------------------------------------------------------------------------------------- salary
    public static final String COLUMN_NAME_TITLE = "title";

    // ------------------------------------------------------------------------------------------------------- from_date
    public static final String COLUMN_NAME_FROM_DATE = "from_date";

    // --------------------------------------------------------------------------------------------------------- to_date
    public static final String COLUMN_NAME_TO_DATE = "to_date";

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ------------------------------------------------------------------------------------------------ java.lang.Object
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Title that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // -------------------------------------------------------------------------------------------------------------- id
    public TitleId getId() {
        return id;
    }

    /**
     * Replace current value of {@code id.empNo} attribute path with specified value.
     *
     * @param idEmpNo new value for the {@code id.empNo} attribute path.
     */
    public void setIdEmpNo(final Integer idEmpNo) {
        assert id != null;
        id.setEmpNo(idEmpNo);
    }

    public void setIdTitle(final String idTitle) {
        assert id != null;
        id.setTitle(idTitle);
    }

    public void setIdFromDate(final LocalDate idFromDate) {
        assert id != null;
        id.setFromDate(idFromDate);
    }

    // -------------------------------------------------------------------------------------------------------- employee

    /**
     * Returns current value of {@link Title_#employee employee} attribute.
     *
     * @return current value of the {@link Title_#employee employee} attribute.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Replace current value of {@link Title_#employee employee} attribute with specified value.
     *
     * @param employee new value for the {@link Title_#employee employee} attribute.
     * @implSpec This method also replaces current value of {@link TitleId_#empNo id.empNo} attribute path with
     * {@code employee?.empNo}.
     */
    public void setEmployee(final Employee employee) {
        this.employee = employee;
        assert id != null;
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
    @Setter(AccessLevel.NONE) // getter-only
    // > The entity class must not be final.
    // > No methods or persistent instance variables of the entity class may be final.
    // https://jakarta.ee/specifications/persistence/3.0/jakarta-persistence-spec-3.0#a18
    private /* final */ TitleId id = new TitleId();

    @Valid
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = COLUMN_NAME_EMP_NO,
            nullable = false,
            insertable = false, // !!!
            updatable = false   // !!!
            )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_TO_DATE, nullable = false, insertable = true, updatable = true)
    private LocalDate toDate;
}
