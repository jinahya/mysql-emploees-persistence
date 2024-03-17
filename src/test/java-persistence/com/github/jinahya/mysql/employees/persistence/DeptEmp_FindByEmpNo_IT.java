package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

class DeptEmp_FindByEmpNo_IT
        extends _BaseEntityIT<DeptEmpLatestDate, Integer> {

    private static DeptEmpLatestDate findByEmpNo1(final EntityManager entityManager, final Integer empNo) {
        final var query = entityManager.createQuery(
                """
                        SELECT e
                        FROM DeptEmpLatestDate AS e
                        WHERE e.empNo = :empNo""",
                DeptEmpLatestDate.class
        );
        query.setParameter("empNo", empNo);
        query.getSingleResult();
        return query.getSingleResult();
    }

    private static DeptEmpLatestDate findByEmpNo2(final EntityManager entityManager, final Integer empNo) {
        final var builder = entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(DeptEmpLatestDate.class);
        final var root = query.from(DeptEmpLatestDate.class);                             // FROM DeptEmpLatestDate AS e
        query.select(root);                                                               // SELECT e
        query.where(builder.equal(root.get("empNo"), empNo));                             // WHERE e.empNo = :empNo
        return entityManager.createQuery(query).getSingleResult();
    }

    static DeptEmpLatestDate findByEmpNo(final EntityManager entityManager, final Integer empNo) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(empNo, "empNo is null");
        if (ThreadLocalRandom.current().nextBoolean()) {
            return findByEmpNo1(entityManager, empNo);
        }
        return findByEmpNo2(entityManager, empNo);
    }

    // -----------------------------------------------------------------------------------------------------------------
    DeptEmp_FindByEmpNo_IT() {
        super(DeptEmpLatestDate.class);
    }
}
