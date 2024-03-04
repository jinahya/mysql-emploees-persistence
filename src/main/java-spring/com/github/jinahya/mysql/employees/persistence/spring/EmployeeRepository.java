package com.github.jinahya.mysql.employees.persistence.spring;

import com.github.jinahya.mysql.employees.persistence.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

@Repository
public interface EmployeeRepository extends _BaseEntityRepository<Employee, Integer> {

    // ------------------------------------------------------------------------------------------------------- birthDate
    Slice<Employee> findAllByBirthDate(LocalDate birthDate, Pageable pageable);

    Slice<Employee> findAllByBirthDateBetween(LocalDate minBirthDateInclusive, LocalDate maxBirthDateInclusive,
                                              Pageable pageable);

//    Slice<Employee> findAllBornOn(YearMonth yearMonth, Pageable pageable);

//    Slice<Employee> findAllBornIn(Year year, Pageable pageable);
}
