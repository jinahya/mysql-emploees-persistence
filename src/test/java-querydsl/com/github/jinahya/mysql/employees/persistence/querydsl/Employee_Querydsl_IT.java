package com.github.jinahya.mysql.employees.persistence.querydsl;

import com.github.jinahya.mysql.employees.persistence.Employee;

class Employee_Querydsl_IT extends _BaseEntity_Querydsl_IT<Employee, Integer> {

    Employee_Querydsl_IT() {
        super(Employee.class);
    }
}
