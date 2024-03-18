package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class Title_Basic_Test
        extends _BaseEntity_Basic_Test<Title, TitleId> {

    Title_Basic_Test() {
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
