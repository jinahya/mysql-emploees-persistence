package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class EmployeeTest extends _BaseEntityTest<Employee, Integer> {

    EmployeeTest() {
        super(Employee.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<Employee> equals__(final SingleTypeEqualsVerifierApi<Employee> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.SURROGATE_KEY);
    }
}
