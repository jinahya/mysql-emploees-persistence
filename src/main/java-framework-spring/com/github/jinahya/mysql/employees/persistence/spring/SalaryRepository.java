package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import com.github.jinahya.mysql.employees.persistence.Salary;
import com.github.jinahya.mysql.employees.persistence.SalaryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository interface for {@link Salary} entity.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see <a
 * href="https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/criteria/criteriabuilder#extract(jakarta.persistence.criteria.TemporalField,jakarta.persistence.criteria.Expression)">CriteriaBuilder#extract</a>
 * (3.2)
 */
@Repository
public interface SalaryRepository extends _BaseEntityRepository<Salary, SalaryId> {

    // -------------------------------------------------------------------------------------------------- empNo/employee
    Page<Salary> findAllByEmpNo(Integer empNo, Pageable pageable);

    Page<Salary> findAllByEmployee(Employee employee, Pageable pageable);

    Optional<Salary> findFirstByEmployeeOrderByFromDateDesc(Employee employee);

    // ---------------------------------------------------------------------------------------------------------- salary
    Page<Salary> findAllBySalaryLessThan(int maximumSalaryExclusive, Pageable pageable);

    Page<Salary> findAllBySalaryGreaterThanEqual(int minimumSalaryInclusive, Pageable pageable);

    // -------------------------------------------------------------------------------------------------------- fromDate

    // ---------------------------------------------------------------------------------------------------------- toDate
}
