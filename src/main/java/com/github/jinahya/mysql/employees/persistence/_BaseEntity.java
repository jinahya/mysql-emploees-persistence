package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
@SuppressWarnings({
        "java:S101", // class _BaseEntity
        "java:S119"  // <ID ...>
})
public abstract class _BaseEntity<ID extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3812806986886347446L;
}
