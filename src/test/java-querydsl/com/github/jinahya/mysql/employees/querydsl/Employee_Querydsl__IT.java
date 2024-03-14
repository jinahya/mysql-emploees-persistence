package com.github.jinahya.mysql.employees.querydsl;

import com.github.jinahya.mysql.employees.persistence.Employee;

abstract class Employee_Querydsl__IT extends _BaseEntity_Querydsl_IT<Employee, Integer> {

    Employee_Querydsl__IT() {
        super(Employee.class);
    }
}
