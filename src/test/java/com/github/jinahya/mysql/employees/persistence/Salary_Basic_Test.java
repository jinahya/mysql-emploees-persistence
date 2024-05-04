package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class Salary_Basic_Test
        extends _BaseEntity_Basic_Test<Salary, SalaryId> {

    Salary_Basic_Test() {
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
