package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

class DeptEmpServiceImpl
        implements DeptEmpService {

    @Override
    public Optional<DeptEmp> current(final @Nonnull Employee employee) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptEmp.class);
        final var root = query.from(DeptEmp.class);
        query.where(
                builder.and(
                        builder.equal(root, employee),
                        builder.equal(root.get(DeptEmp_.toDate), DeptEmp.ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED)
                )
        );
        try {
            return Optional.of(
                    entityManager.createQuery(query).getSingleResult() // NonUniqueResultException
            );
        } catch (final NoResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    public void assign(@Nonnull Employee employee, @Nonnull Department department, @Nonnull LocalDate fromDate) {
        final var current = current(employee).orElse(null);
        if (current != null) {
            if (Objects.equals(current.getDeptNo(), department.getDeptNo())) {
                return;
            }
            current.setToDate(fromDate);
        }
        final var entity = new DeptEmp();
        entity.setEmployee(employee);
        entity.setDepartment(department);
        entity.setFromDate(fromDate);
        entity.setToDate(DeptEmp.ATTRIBUTE_VALUE_TO_DATE_UNSPECIFIED);
        entityManager.persist(entity);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private EntityManager entityManager;
}
