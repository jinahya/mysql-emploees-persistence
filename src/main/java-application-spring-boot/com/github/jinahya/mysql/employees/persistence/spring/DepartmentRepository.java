package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.Department_;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository
        extends _BaseEntityRepository<Department, String> {

    /**
     * Finds the entity whose {@link Department_#deptName deptName} attribute matches specified value.
     *
     * @param deptName the value of {@link Department_#deptName deptName} attribute to match.
     * @return an optional of found entity; {@link Optional#empty() empty} if not found.
     */
    Optional<Department> findByDeptName(String deptName);
}
