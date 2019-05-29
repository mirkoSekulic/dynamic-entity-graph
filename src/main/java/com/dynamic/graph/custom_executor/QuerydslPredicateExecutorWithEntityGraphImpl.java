package com.dynamic.graph.custom_executor;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuerydslPredicateExecutorWithEntityGraphImpl<T> extends QuerydslJpaPredicateExecutor<T> implements QuerydslPredicateExecutorWithEntityGraph<T> {

    private final EntityPath<T> path;
    private final Querydsl querydsl;
    private final EntityManager entityManager;
    private final JpaEntityInformation entityInformation;
    private final CrudMethodMetadata metadata;

    public QuerydslPredicateExecutorWithEntityGraphImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, EntityPathResolver resolver, @Nullable CrudMethodMetadata metadata) {
        super(entityInformation, entityManager, resolver, metadata);
        this.path = resolver.createPath(entityInformation.getJavaType());
        this.querydsl = new Querydsl(entityManager, new PathBuilder(this.path.getType(), this.path.getMetadata()));
        this.entityInformation = entityInformation;
        this.metadata = metadata;
        this.entityManager = entityManager;
    }

    @Override
    public Page<T> findAll(Predicate predicate, JpaEntityGraph dynamicEntityGraph, Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null!");
        JPQLQuery<?> countQuery = this.createCountQuery(predicate);
        JPQLQuery<T> query = this.querydsl.applyPagination(pageable, this.createQueryWithDynamicGraph(dynamicEntityGraph, predicate).select(this.path));
        List var10000 = query.fetch();
        countQuery.getClass();
        return PageableExecutionUtils.getPage(var10000, pageable, countQuery::fetchCount);
    }

    protected JPQLQuery<?> createQueryWithDynamicGraph(JpaEntityGraph dynamicEntityGraph, Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = this.doCreateQueryWithGraph(dynamicEntityGraph, predicate);
        CrudMethodMetadata metadata = this.metadata;
        if (metadata == null) {
            return query;
        } else {
            LockModeType type = metadata.getLockModeType();
            return type == null ? query : query.setLockMode(type);
        }
    }

    private AbstractJPAQuery<?, ?> doCreateQueryWithGraph(JpaEntityGraph dynamicEntityGraph, @Nullable Predicate... predicate) {
        AbstractJPAQuery<?, ?> query = this.querydsl.createQuery(new EntityPath[]{this.path});
        if (predicate != null) {
            query = (AbstractJPAQuery)query.where(predicate);
        }

        Map<String, Object> entityGraphHints = new HashMap<>(
                Jpa21Utils.tryGetFetchGraphHints(this.entityManager, dynamicEntityGraph, entityInformation.getJavaType())
        );

        for (Map.Entry<String, Object> hint : entityGraphHints.entrySet()) {
            query.setHint(hint.getKey(), hint.getValue());
        }

        return query;
    }
}
