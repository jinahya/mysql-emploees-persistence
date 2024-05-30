package com.github.jinahya.mysql.employees.persistence;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;

import java.util.concurrent.ThreadLocalRandom;

abstract class DeptEmp__IT
        extends _BaseEntityIT<DeptEmp, DeptEmpId> {

    // -----------------------------------------------------------------------------------------------------------------
    private static Employee selectRandomEmployee_QueryLanguage(final @Nonnull EntityManager entityManager) {
        final var count = __Persistence_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.employee FROM DeptEmp AS e", Employee.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult(); // NoResultException
    }

    private static Employee selectRandomEmployee_CriteriaApi(final @Nonnull EntityManager entityManager) {
        final var count = __Persistence_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.employee FROM DeptEmp AS e", Employee.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult(); // NoResultException
    }

    static Employee selectRandomEmployee(final @Nonnull EntityManager entityManager) {
        return ThreadLocalRandom.current().nextBoolean()
                ? selectRandomEmployee_QueryLanguage(entityManager)
                : selectRandomEmployee_CriteriaApi(entityManager);
    }

    private static
    @Nonnull Integer selectRandomEmpNo_QueryLanguage(final @Nonnull EntityManager entityManager) {
        final var count = __Persistence_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.empNo FROM DeptEmp AS e", Integer.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult(); // NoResultException
    }

    private static
    @Nonnull Integer selectRandomEmpNo_CriteriaApi(final @Nonnull EntityManager entityManager) {
        final var count = __Persistence_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Integer.class);
        final var root = query.from(DeptEmp.class);                                                 // FROM DeptEmp AS e
        query.select(root.get(DeptEmp_.empNo));                                                     // SELECT e.empNo
        return entityManager.createQuery(query)
                .setFirstResult(startPosition)                                                      // offset
                .setMaxResults(1)                                                                   // limit
                .getSingleResult(); // NoResultException
    }

    static
    @Nonnull Integer selectRandomEmpNo(final @Nonnull EntityManager entityManager) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return selectRandomEmployee(entityManager).getEmpNo();
        }
        return ThreadLocalRandom.current().nextBoolean()
                ? selectRandomEmpNo_QueryLanguage(entityManager)
                : selectRandomEmpNo_CriteriaApi(entityManager);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static Department selectRandomDepartment_QueryLanguage(final @Nonnull EntityManager entityManager) {
        final var count = __Persistence_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.department FROM DeptEmp AS e", Department.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult();
    }

    private static Department selectRandomDepartment_CriteriaApi(final @Nonnull EntityManager entityManager) {
        final var count = __Persistence_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.department FROM DeptEmp AS e", Department.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult();
    }

    static Department selectRandomDepartment(final @Nonnull EntityManager entityManager) {
        return ThreadLocalRandom.current().nextBoolean()
                ? selectRandomDepartment_QueryLanguage(entityManager)
                : selectRandomDepartment_CriteriaApi(entityManager);
    }

    private static
    @Nonnull String selectRandomDeptNo_QueryLanguage(final @Nonnull EntityManager entityManager) {
        final var count = __Persistence_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        return entityManager
                .createQuery("SELECT e.deptNo FROM DeptEmp AS e", String.class)
                .setFirstResult(startPosition)
                .setMaxResults(1)
                .getSingleResult();
    }

    private static
    @Nonnull String selectRandomDeptNo_CriteriaApi(final @Nonnull EntityManager entityManager) {
        final var count = __Persistence_Utils.selectCount(entityManager, DeptEmp.class);
        final var startPosition = Math.toIntExact(ThreadLocalRandom.current().nextLong(count));
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(String.class);
        final var root = query.from(DeptEmp.class);                                                 // FROM DeptEmp AS e
        query.select(root.get(DeptEmp_.deptNo));                                                    // SELECT e.deptNo
        return entityManager.createQuery(query)
                .setFirstResult(startPosition)                                                      // offset
                .setMaxResults(1)                                                                   // limit
                .getSingleResult();
    }

    static
    @Nonnull String selectRandomDeptNo(final @Nonnull EntityManager entityManager) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return selectRandomDepartment(entityManager).getDeptNo();
        }
        return ThreadLocalRandom.current().nextBoolean()
                ? selectRandomDeptNo_QueryLanguage(entityManager)
                : selectRandomDeptNo_CriteriaApi(entityManager);
    }

    // -----------------------------------------------------------------------------------------------------------------
    DeptEmp__IT() {
        super(DeptEmp.class);
    }
}
