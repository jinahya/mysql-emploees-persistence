package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class TitleTest extends _BaseEntityTest<Title, TitleId> {

    TitleTest() {
        super(Title.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<Title> equals__(final SingleTypeEqualsVerifierApi<Title> verifierApi) {
        return super.equals__(verifierApi)
                .suppress(Warning.SURROGATE_KEY)
                .withPrefabValues(Employee.class, Employee.of(0), Employee.of(1))
                ;
    }
}
