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

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

@Slf4j
final class __LangUtils {

    static <T extends AutoCloseable> T uncloseable(final Class<T> interfaceClass, Method method,
                                                   final T closeableInstance) {
        Objects.requireNonNull(interfaceClass, "interfaceClass is null");
        Objects.requireNonNull(closeableInstance, "closeableInstance is null");
        if (method == null) {
            try {
                method = AutoCloseable.class.getMethod("close");
                assert method.canAccess(closeableInstance);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        final Method method_ = method;
        @SuppressWarnings({"unchecked"}) final T proxy = (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                (p, m, args) -> {
                    log.debug("m: {}, method: {}", m, method_);
                    if (m == method_) {
                        throw new RuntimeException("not supposed to close " + p);
                    }
                    return m.invoke(closeableInstance, args);
                }
        );
        return proxy;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private __LangUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
