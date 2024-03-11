package com.github.jinahya.mysql.employees.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * An abstract base class for testing subclasses of {@link _BaseEntity} class.
 *
 * @param <ENTITY> {@link _BaseEntity entity} type parameter
 * @param <ID>     the type of the {@code ID} of {@link ENTITY}.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class __BaseEntityTest<ENTITY extends _BaseEntity<ID>, ID extends Serializable> {

    @Deprecated(forRemoval = true)
    __BaseEntityTest(final Class<ENTITY> entityClass, final Class<ID> idClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
        this.idClass = idClass;
    }

    /**
     * Creates a new instance for testing specified entity instance.
     *
     * @param entityClass the entity instance to test.
     * @see #entityClass
     */
    __BaseEntityTest(final Class<ENTITY> entityClass) {
        this(entityClass, null);
    }

    // ----------------------------------------------------------------------------------------------------- entityClass

    /**
     * Returns a new instance of {@link #entityClass}.
     *
     * @return a new instance of {@link #entityClass}.
     */
    ENTITY newEntityInstance() {
        try {
            final var constructor = entityClass.getDeclaredConstructor();
            if (!constructor.canAccess(null)) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        } catch (final ReflectiveOperationException roe) {
            throw new RuntimeException("failed to instantiate " + entityClass, roe);
        }
    }

    // --------------------------------------------------------------------------------------------------------- idClass

    /**
     * Returns the class of {@link ID}.
     *
     * @return the class of {@link ID}.
     */
    // https://stackoverflow.com/a/28209213/330457
    @SuppressWarnings({"unchecked"})
    final Class<ID> idClass() {
        if (true) {
            return _BaseEntityTestUtils.idClass(entityClass);
        }
        if (idClass == null) {
            var genericSuperclass = entityClass.getGenericSuperclass();
            ParameterizedType parameterizedType = null;
            while (parameterizedType == null) {
                if (genericSuperclass instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) genericSuperclass;
                } else {
                    genericSuperclass = ((Class<?>) genericSuperclass).getGenericSuperclass();
                }
            }
            idClass = (Class<ID>) parameterizedType.getActualTypeArguments()[0];
        }
        return idClass;
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The entity class to test.
     */
    final Class<ENTITY> entityClass;

    /**
     * The {@link ID} class of the {@link #entityClass}.
     *
     * @see #idClass()
     */
    private Class<ID> idClass;
}
