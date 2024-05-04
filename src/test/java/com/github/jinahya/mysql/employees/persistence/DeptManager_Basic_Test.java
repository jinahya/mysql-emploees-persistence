package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class DeptManager_Basic_Test
        extends _BaseEntity_Basic_Test<DeptManager, DeptManagerId> {

    DeptManager_Basic_Test() {
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
