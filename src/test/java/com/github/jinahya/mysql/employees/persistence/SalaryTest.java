package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class SalaryTest extends _BaseEntityTest<Salary, SalaryId> {

    SalaryTest() {
        super(Salary.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<Salary> equals__(final SingleTypeEqualsVerifierApi<Salary> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .withPrefabValues(Employee.class, Employee.of(0), Employee.of(1))
                ;
    }
}
