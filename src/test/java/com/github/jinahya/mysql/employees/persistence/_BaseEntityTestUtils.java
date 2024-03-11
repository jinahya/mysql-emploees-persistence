package com.github.jinahya.mysql.employees.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class _BaseEntityTestUtils {

    // --------------------------------------------------------------------------------------------------------- idClass
    private static final Map<Class<?>, Class<?>> ID_CLASSES = new ConcurrentHashMap<>();

    // https://stackoverflow.com/a/28209213/330457
    @SuppressWarnings({
            "unchecked", // (Class<ID>)
            "java:S119" // <ENTITY ...>
    })
    public static <ENTITY extends _BaseEntity<ID>, ID extends Serializable> Class<ID> idClass(
            final Class<ENTITY> entityClass) {
        return (Class<ID>) ID_CLASSES.computeIfAbsent(entityClass, k -> {
            var genericSuperclass = k.getGenericSuperclass();
            ParameterizedType parameterizedType = null;
            while (parameterizedType == null) {
                if (genericSuperclass instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) genericSuperclass;
                } else {
                    genericSuperclass = ((Class<?>) genericSuperclass).getGenericSuperclass();
                }
            }
            return (Class<ID>) parameterizedType.getActualTypeArguments()[0];
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    private _BaseEntityTestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
