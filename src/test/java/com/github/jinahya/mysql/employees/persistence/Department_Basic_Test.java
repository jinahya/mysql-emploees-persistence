package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class Department_Basic_Test
        extends _BaseEntity_Basic_Test<Department, String> {

    Department_Basic_Test() {
        super(Department.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    SingleTypeEqualsVerifierApi<Department> equals__(final SingleTypeEqualsVerifierApi<Department> verifierApi) {
        return super.equals__(verifierApi)
                    .suppress(Warning.SURROGATE_KEY)
                    .withPrefabValues(Employee.class, Employee.of(0), Employee.of(1))
                ;
    }
}
