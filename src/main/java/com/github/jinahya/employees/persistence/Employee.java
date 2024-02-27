package com.github.jinahya.employees.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = Employee.TABLE_NAME)
public class Employee implements Serializable {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String TABLE_NAME = "employee";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EMP_NO = "emp_no";

    public static final String COLUMN_NAME_BIRTH_DATE = "birth_date";

    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    public static final String COLUMN_NAME_GENDER = "gender";

    public static final String COLUMN_VALUE_GENER_F = "F";

    public static final String COLUMN_VALUE_GENER_M = "M";

    public enum Gender {
        F,
        M
    }

    public static final String COLUMN_NAME_HIRE_DATE = "hire_date";

    // -----------------------------------------------------------------------------------------------------------------
    @Id
    @Column(name = COLUMN_NAME_EMP_NO, nullable = false, insertable = true, updatable = false)
    private Integer empNo;
}
