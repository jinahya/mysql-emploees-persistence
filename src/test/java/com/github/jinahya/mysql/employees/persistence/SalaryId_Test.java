package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class SalaryId_Test
        extends _BaseId_Test<SalaryId> {

    SalaryId_Test() {
        super(SalaryId.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<SalaryId> equals__(final SingleTypeEqualsVerifierApi<SalaryId> verifierApi) {
        return super.equals__(verifierApi)
                    .suppress(Warning.NONFINAL_FIELDS);
    }
}
