package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Department;
import com.github.jinahya.mysql.employees.persistence.DeptEmp;
import com.github.jinahya.mysql.employees.persistence.DeptEmpId;
import com.github.jinahya.mysql.employees.persistence.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * A repository interface for {@link DeptEmp} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Repository
public interface DeptEmpRepository
        extends _BaseEntityRepository<DeptEmp, DeptEmpId> {

    // -------------------------------------------------------------------------------------------------- empNo/employee
    Page<DeptEmp> findAllByEmployee(Employee employee, Pageable pageable);

    // ----------------------------------------------------------------------------------------------- deptNo/department
    Page<DeptEmp> findAllByDepartment(Department department, Pageable pageable);

    // --------------------------------------------------------------------------------------------- employee/department
    @Deprecated
    Optional<DeptEmp> findByEmployeeAndDepartment(Employee employee, Department department);

    default Optional<DeptEmp> findByEmployeeAndDepartment1(final Employee employee, final Department department) {
        return findById(
                DeptEmpId.from(employee, department)
        );
    }

    // -------------------------------------------------------------------------------------------------------- fromDate

    // ---------------------------------------------------------------------------------------------------------- toDate

    // -----------------------------------------------------------------------------------------------------------------
    Page<DeptEmp> findAllByDepartmentAndFromDateGreaterThanEqualAndToDateBefore(Department department,
                                                                                LocalDate fromDateMinInclusive,
                                                                                LocalDate toDateMaxInclusive,
                                                                                Pageable pageable);
}
