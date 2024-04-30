package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class Employee_Basic_Test
        extends _BaseEntity_Basic_Test<Employee, Integer> {

    Employee_Basic_Test() {
        super(Employee.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    SingleTypeEqualsVerifierApi<Employee> equals__(final SingleTypeEqualsVerifierApi<Employee> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.SURROGATE_KEY)
                .withPrefabValues(Employee.class, Employee.of(0), Employee.of(1))
                ;
    }
}
