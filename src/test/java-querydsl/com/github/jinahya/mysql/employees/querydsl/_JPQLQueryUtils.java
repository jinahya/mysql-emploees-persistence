package com.github.jinahya.mysql.employees.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.JPQLQuery;

import java.util.Objects;
import java.util.function.Function;

/**
 * Utilities for {@link JPQLQuery} interface.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
final class _JPQLQueryUtils {

    static <T, U extends EntityPath<T>, V> T fetchOneById(
            final JPQLQuery<T> query,
            final U entityPath,
            final Function<? super U, ? extends SimpleExpression<? super V>> idExpressionMapper,
            final V idValue) {
        Objects.requireNonNull(query, "query is null");
        Objects.requireNonNull(entityPath, "entityPath is null");
        Objects.requireNonNull(idExpressionMapper, "idExpressionMapper is null");
        Objects.requireNonNull(idValue, "idValue is null");
        return query.select(entityPath)
                    .from(entityPath)
                    .where(idExpressionMapper.apply(entityPath).eq(idValue))
                    .fetchOne(); // NonUniqueResultException
    }

    static <T, U extends EntityPath<T>, V> T fetchOneById(
            final JPQLQuery<T> query,
            final U entityPath,
            final SimpleExpression<? super V> idExpression,
            final V idValue) {
        Objects.requireNonNull(query, "query is null");
        Objects.requireNonNull(entityPath, "entityPath is null");
        Objects.requireNonNull(idExpression, "idExpression is null");
        Objects.requireNonNull(idValue, "idValue is null");
        return fetchOneById(query, entityPath, ep -> idExpression, idValue);
    }

    private _JPQLQueryUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
