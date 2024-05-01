package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;

import java.util.concurrent.ThreadLocalRandom;

abstract class DeptEmp__IT
        extends _BaseEntityIT<DeptEmp, DeptEmpId> {

    // -----------------------------------------------------------------------------------------------------------------
    private static Employee selectRandomEmployeeUsingQueryLanguage(final @Nonnull EntityManager entityManager) {
        final var count = _BaseEntityIT_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.employee FROM DeptEmp AS e", Employee.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult();
    }

    private static Employee selectRandomEmployeeUsingCriteriaApi(final @Nonnull EntityManager entityManager) {
        final var count = _BaseEntityIT_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.employee FROM DeptEmp AS e", Employee.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult();
    }

    static Employee selectRandomEmployee(final @Nonnull EntityManager entityManager) {
        return ThreadLocalRandom.current().nextBoolean()
                ? selectRandomEmployeeUsingQueryLanguage(entityManager)
                : selectRandomEmployeeUsingCriteriaApi(entityManager);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static
    @Nonnull Integer selectRandomEmpNoUsingQueryLanguage(final @Nonnull EntityManager entityManager) {
        final var count = _BaseEntityIT_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.empNo FROM DeptEmp AS e", Integer.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult();
    }

    private static
    @Nonnull Integer selectRandomEmpNoUsingCriteriaApi(final @Nonnull EntityManager entityManager) {
        final var count = _BaseEntityIT_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Integer.class);
        final var root = query.from(DeptEmp.class);                                                 // FROM DeptEmp AS e
        query.select(root.get(DeptEmp_.empNo));                                                     // SELECT e.empNo
        return entityManager.createQuery(query)
                .setFirstResult(startPosition)                                                      // offset
                .setMaxResults(1)                                                                   // limit
                .getSingleResult();
    }

    static
    @Nonnull Integer selectRandomEmpNo(final @Nonnull EntityManager entityManager) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return selectRandomEmployee(entityManager).getEmpNo();
        }
        return ThreadLocalRandom.current().nextBoolean()
                ? selectRandomEmpNoUsingQueryLanguage(entityManager)
                : selectRandomEmpNoUsingCriteriaApi(entityManager);
    }

    // -----------------------------------------------------------------------------------------------------------------
    DeptEmp__IT() {
        super(DeptEmp.class);
    }
}
