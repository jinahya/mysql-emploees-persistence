package com.github.jinahya.mysql.employees.persistence;

import nl.jqno.equalsverifier.Warning;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;

class CurrentDeptEmp_Test
        extends _BaseEntityTest<CurrentDeptEmp, CurrentDeptEmpId> {

    CurrentDeptEmp_Test() {
        super(CurrentDeptEmp.class);
    }

    @Override
    SingleTypeEqualsVerifierApi<CurrentDeptEmp> equals__(
            final SingleTypeEqualsVerifierApi<CurrentDeptEmp> verifierApi) {
        return super.equals__(verifierApi)
                    .suppress(Warning.SURROGATE_KEY)
                    .withPrefabValues(DeptEmp.class, DeptEmp.of(0, "1"), DeptEmp.of(1, "2"))
//                .withIgnoredFields("deptEmp")
                ;
    }
}
