package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class DeptManagerTest extends BaseEntityTest<DeptManager> {

    DeptManagerTest() {
        super(DeptManager.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<DeptManager> equals__(final SingleTypeEqualsVerifierApi<DeptManager> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.SURROGATE_KEY);
    }
}
