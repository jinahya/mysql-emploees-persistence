package com.github.jinahya.mysql.employees.persistence;

public final class DeptEmpConstants {

    public static final String NAMED_QUERY_NAME_SELECT_ALL_BY_EMP_NO = "DeptEmp.selectAllByEmpNo";

    public static final String NAMED_QUERY_NAME_SELECT_ALL_BY_DEPT_NO = "DeptEmp.selectAllByDeptNo";

    // -----------------------------------------------------------------------------------------------------------------
    private DeptEmpConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
