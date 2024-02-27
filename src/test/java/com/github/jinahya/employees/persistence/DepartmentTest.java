package com.github.jinahya.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class DepartmentTest extends BaseEntityTest<Department> {

    DepartmentTest() {
        super(Department.class);
    }

    @Override
    void equals__(final SingleTypeEqualsVerifierApi<Department> verifierApi) {
        super.equals__(verifierApi);
        verifierApi.suppress(Warning.SURROGATE_KEY);
    }
}