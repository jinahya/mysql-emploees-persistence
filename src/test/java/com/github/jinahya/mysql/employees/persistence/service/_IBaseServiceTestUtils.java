package com.github.jinahya.mysql.employees.persistence.service;

import com.github.jinahya.mysql.employees.persistence._BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public final class _IBaseServiceTestUtils {

    // ----------------------------------------------------------------------------------------------------- entityClass
    private static final Map<Class<?>, Class<?>> ENTITY_CLASSES = new ConcurrentHashMap<>();

    // https://stackoverflow.com/a/28209213/330457
    @SuppressWarnings({
            "unchecked", // (Class<ID>)
            "java:S119" // <SERVICE ...>
    })
    public static <SERVICE extends _IBaseEntityService<ENTITY, ?>, ENTITY extends _BaseEntity<?>> Class<ENTITY>
    entityClass(final Class<SERVICE> serviceClass) {
        return (Class<ENTITY>) ENTITY_CLASSES.computeIfAbsent(serviceClass, k -> {
            var genericSuperclass = k.getGenericSuperclass();
            ParameterizedType parameterizedType = null;
            while (parameterizedType == null) {
                if (genericSuperclass instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) genericSuperclass;
                } else {
                    genericSuperclass = ((Class<?>) genericSuperclass).getGenericSuperclass();
                }
            }
            return (Class<ENTITY>) parameterizedType.getActualTypeArguments()[0];
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    private _IBaseServiceTestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
