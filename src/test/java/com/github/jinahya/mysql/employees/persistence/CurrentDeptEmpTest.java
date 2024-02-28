package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class CurrentDeptEmpTest extends BaseEntityTest<CurrentDeptEmp> {

    CurrentDeptEmpTest() {
        super(CurrentDeptEmp.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<CurrentDeptEmp> equals__(final SingleTypeEqualsVerifierApi<CurrentDeptEmp> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.SURROGATE_KEY);
    }
}
