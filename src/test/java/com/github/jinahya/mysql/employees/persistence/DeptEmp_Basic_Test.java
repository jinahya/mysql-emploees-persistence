package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class DeptEmp_Basic_Test
        extends _BaseEntity_Basic_Test<DeptEmp, DeptEmpId> {

    DeptEmp_Basic_Test() {
        super(DeptEmp.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    SingleTypeEqualsVerifierApi<DeptEmp> equals__(final SingleTypeEqualsVerifierApi<DeptEmp> verifierApi) {
        return super.equals__(verifierApi)
                    .suppress(Warning.SURROGATE_KEY)
                    .withPrefabValues(Employee.class, Employee.of(0), Employee.of(1))
                ;
    }
}
