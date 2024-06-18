package com.github.jinahya.mysql.employees.persistence;

import org.mockito.Mockito;

import java.io.Serializable;
import java.util.Objects;

/**
 * An abstract base class for testing subclasses of {@link _BaseEntity} class.
 *
 * @param <ENTITY> {@link _BaseEntity entity} type parameter
 * @param <ID>     the type of the {@code ID} of {@link ENTITY}.
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
abstract class __BaseEntity__Test<ENTITY extends _BaseEntity<ID>, ID extends Serializable> {

    /**
     * Creates a new instance for testing specified entity instance.
     *
     * @param entityClass the entity instance to test.
     * @see #entityClass
     */
    __BaseEntity__Test(final Class<ENTITY> entityClass) {
        super();
        this.entityClass = Objects.requireNonNull(entityClass, "entityClass is null");
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

    /**
     * Returns a spy of {@link #newEntityInstance()}.
     *
     * @return a spy of {@link #newEntityInstance()}.
     */
    ENTITY newEntitySpy() {
        return Mockito.spy(newEntityInstance());
    }

    // --------------------------------------------------------------------------------------------------------- idClass

    /**
     * Returns the class of {@link ID}.
     *
     * @return the class of {@link ID}.
     */
    // https://stackoverflow.com/a/28209213/330457
    final Class<ID> idClass() {
        return _BaseEntityTestUtils.idClass(entityClass);
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
