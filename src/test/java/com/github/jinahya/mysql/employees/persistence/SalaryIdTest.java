package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class SalaryIdTest extends _BaseIdTest<SalaryId> {

    SalaryIdTest() {
        super(SalaryId.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<SalaryId> equals__(final SingleTypeEqualsVerifierApi<SalaryId> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.NONFINAL_FIELDS);
    }
}