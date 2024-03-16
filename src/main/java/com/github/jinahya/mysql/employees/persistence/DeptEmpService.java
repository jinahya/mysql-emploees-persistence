package com.github.jinahya.mysql.employees.persistence;

public interface DeptEmpService extends _BaseEntityService<DeptEmp> {

    boolean deploy(Employee employee, Department department);

    /**
     * Un-deploys specified employee from specified department.
     *
     * @param employee   the employ to be un-deployed from the {@code department}.
     * @param department the department from which the {@code employee} is un-deployed.
     * @return {@code true} if the {@code employee} has been deployed to the {@code department} (and un-deployed);
     * {@code false} when the {@code employee} has never been deployed to the {@code department} or already
     * un-deployed.
     */
    boolean undeploy(Employee employee, Department department);
}
