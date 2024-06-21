package com.github.jinahya.mysql.employees.persistence;

import com.github.jinahya.mysql.employees.persistence.service.IDeptEmpService;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class DeptEmpServiceImpl
        implements IDeptEmpService {

    @Override
    public List<@Valid @NotNull DeptEmp> getAssignments(@Nonnull Employee employee, @Nonnull LocalDate date) {
        return List.of();
    }

    @Override
    public void unassign(@Nonnull Employee employee, @Nonnull Department department, LocalDate toDate) {

    }

    @Override
    public @Nonnull Optional<DeptEmp> findEntity(@Nonnull DeptEmpId deptEmpId) {
        return Optional.empty();
    }

    @Override
    public @Nonnull DeptEmp persistEntity(@Nonnull DeptEmp entity) {
        return null;
    }

    @Override
    public List<DeptEmp> getCurrentAssignments(final @Nonnull Employee employee) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptEmp.class);
        final var root = query.from(DeptEmp.class);
        query.where(
                builder.and(
                        builder.equal(root.get(DeptEmp_.employee), employee),
                        builder.greaterThanOrEqualTo(root.get(DeptEmp_.fromDate), builder.localDate()),
                        builder.lessThanOrEqualTo(root.get(DeptEmp_.toDate), builder.localDate())
                )
        );
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void assign(@Nonnull Employee employee, @Nonnull Department department, @Nonnull LocalDate fromDate) {
    }

    @Override
    public void unassign(@Nonnull Employee employee, @Nonnull Department department) {

    }

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private EntityManager entityManager;
}
