package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class DeptManagerTest extends _BaseEntityTest<DeptManager, DeptManagerId> {

    DeptManagerTest() {
        super(DeptManager.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    SingleTypeEqualsVerifierApi<DeptManager> equals__(final SingleTypeEqualsVerifierApi<DeptManager> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.SURROGATE_KEY)
                .withPrefabValues(Employee.class, Employee.of(0), Employee.of(1))
                ;
    }
}
