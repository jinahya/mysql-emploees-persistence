package com.github.jinahya.mysql.employees.persistence;

import java.util.Objects;

abstract class __BaseId_Test<ID extends _BaseId> {

    // -----------------------------------------------------------------------------------------------------------------
    __BaseId_Test(final Class<ID> idClass) {
        super();
        this.idClass = Objects.requireNonNull(idClass, "idClass is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    final Class<ID> idClass;
}
