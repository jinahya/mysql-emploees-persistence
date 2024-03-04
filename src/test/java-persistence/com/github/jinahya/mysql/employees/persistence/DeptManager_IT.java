package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

class DeptManager_IT extends _BaseEntityIT<DeptManager, DeptManagerId> {

    private static Department findByDeptNo1(final EntityManager entityManager, final String deptNo) {
        final var query = entityManager.createQuery(
                "SELECT e FROM Department AS e WHERE e.deptNo = :deptNo",
                Department.class
        );
        query.setParameter("deptNo", deptNo);
        query.getSingleResult();
        return query.getSingleResult();
    }

    private static Department findByDeptNo2(final EntityManager entityManager, final String deptNo) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Department.class);
        final var root = query.from(Department.class);                                       // FROM Department AS e
        query.select(root);                                                                  // SELECT e
        query.where(builder.equal(root.get("deptNo"), deptNo));                              // WHERE e.deptNo = :deptNo
        return entityManager.createQuery(query).getSingleResult();
    }

    static Department findByDeptNo(final EntityManager entityManager, final String deptNo) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(deptNo, "deptNo is null");
        if (ThreadLocalRandom.current().nextBoolean()) {
            return findByDeptNo1(entityManager, deptNo);
        }
        return findByDeptNo2(entityManager, deptNo);
    }

    DeptManager_IT() {
        super(DeptManager.class);
    }
}
