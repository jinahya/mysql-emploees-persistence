package com.github.jinahya.mysql.employees.persistence;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
final class __Lang_Utils {

    static <T extends AutoCloseable> T uncloseable(final Class<T> interfaceClass, Method method,
                                                   final T closeableInstance) {
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
    private __Lang_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
