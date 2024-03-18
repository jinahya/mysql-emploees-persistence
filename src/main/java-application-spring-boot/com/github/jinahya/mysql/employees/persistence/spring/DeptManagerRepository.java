package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.DeptManager;
import com.github.jinahya.mysql.employees.persistence.DeptManagerId;
import com.github.jinahya.mysql.employees.persistence.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * A repository interface for {@link DeptManager} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Repository
public interface DeptManagerRepository
        extends _BaseEntityRepository<DeptManager, DeptManagerId> {

    // -------------------------------------------------------------------------------------------------------------- id

    // -------------------------------------------------------------------------------------------------------- employee
    Page<DeptManager> findAllByEmployee(Employee employee, Pageable pageable);

    // ------------------------------------------------------------------------------------------------------ department
    Page<DeptManager> findAllByDepartment(Department department, Pageable pageable);

    // -------------------------------------------------------------------------------------------------------- fromDate

    // ---------------------------------------------------------------------------------------------------------- toDate
}
