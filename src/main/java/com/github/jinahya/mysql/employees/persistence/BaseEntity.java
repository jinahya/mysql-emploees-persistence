package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
abstract class BaseEntity<ID extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3812806986886347446L;
}
