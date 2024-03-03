package com.github.jinahya.mysql.employees.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class _BaseEntityHelper {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Map<Class<?>, Class<?>> ID_CLASSES = new HashMap<>();

    /**
     * Returns the class of {@link ID} of given entity class.
     *
     * @param entityClass the entity class.
     * @param <ENTITY>    entity type parameter
     * @param <ID>        id type parameter
     * @return the class of {@link ID}.
     */
    // https://stackoverflow.com/a/28209213/330457
    @SuppressWarnings({"unchecked"})
    static <ENTITY extends _BaseEntity<ID>, ID extends Serializable> Class<ID> idClass(
            final Class<ENTITY> entityClass) {
        Objects.requireNonNull(entityClass, "entityClass is null");
        return (Class<ID>) ID_CLASSES.computeIfAbsent(
                entityClass,
                k -> {
                    var genericSuperclass = k.getGenericSuperclass();
                    ParameterizedType parameterizedType = null;
                    while (parameterizedType == null) {
                        if (genericSuperclass instanceof ParameterizedType casted) {
                            parameterizedType = casted;
                        } else {
                            genericSuperclass = ((Class<?>) genericSuperclass).getGenericSuperclass();
                        }
                    }
                    return (Class<?>) parameterizedType.getActualTypeArguments()[0];
                }
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final Map<Class<?>, String> ENTITY_NAMES = new HashMap<>();

    /**
     * Returns the {@link EntityType#getName() entityName} of specified entity class.
     *
     * @param entityManager an entity manager.
     * @param entityClass   the entity class.
     * @param <ENTITY>      entity type parameter
     * @return the {@link EntityType#getName() entityName} of {@code entityClass}.
     */
    static <ENTITY extends _BaseEntity<?>> String entityName(final EntityManager entityManager,
                                                             final Class<ENTITY> entityClass) {
        Objects.requireNonNull(entityManager, "entityManager is null");
        Objects.requireNonNull(entityClass, "entityClass is null");
        return ENTITY_NAMES.computeIfAbsent(
                entityClass,
                k -> entityManager.getMetamodel().entity(k).getName()
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private _BaseEntityHelper() {
        throw new AssertionError("instantiation is not allowed");
    }
}
