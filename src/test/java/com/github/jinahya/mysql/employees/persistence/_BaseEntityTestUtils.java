package com.github.jinahya.mysql.employees.persistence;

/*-
 * #%L
 * mysql-employees-persistece
 * %%
 * Copyright (C) 2024 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
    static boolean hasBeenWovenForEclipseLink(final Class<?> entityClass) {
        return Boolean.parseBoolean(System.getProperty("eclipselink.woven"));
    }

    // -----------------------------------------------------------------------------------------------------------------
    private _BaseEntityTestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
