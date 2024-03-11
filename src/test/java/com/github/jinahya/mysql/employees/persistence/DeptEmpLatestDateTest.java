package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class DeptEmpLatestDateTest extends _BaseEntityTest<DeptEmpLatestDate, Integer> {

    DeptEmpLatestDateTest() {
        super(DeptEmpLatestDate.class);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    SingleTypeEqualsVerifierApi<DeptEmpLatestDate> equals__(
            final SingleTypeEqualsVerifierApi<DeptEmpLatestDate> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.SURROGATE_KEY)
                .withPrefabValues(Employee.class, Employee.of(0), Employee.of(1))
                ;
    }
}
