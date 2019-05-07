package com.dynamic.graph.custom_executor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"squid:S00119", "unchecked"})
@NoRepositoryBean
public class CustomSimpleRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements JpaSpecificationExecutorWithEntityGraph<T> {

    private final EntityManager entityManager;

    public CustomSimpleRepository(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAll(JpaEntityGraph dynamicEntityGraph) {
        TypedQuery<T> query = getQuery((Specification) null, Sort.unsorted());
        Map<String, Object> entityGraphHints = new HashMap<>(
                Jpa21Utils.tryGetFetchGraphHints(this.entityManager, dynamicEntityGraph, getDomainClass())
        );
        applyEntityGraphQueryHints(query, entityGraphHints);
        return query.getResultList();
    }

    @Override
    public List<T> findAll(JpaEntityGraph dynamicEntityGraph, Sort sort) {
        TypedQuery<T> query = getQuery((Specification) null, sort);
        Map<String, Object> entityGraphHints = new HashMap<>(
                Jpa21Utils.tryGetFetchGraphHints(this.entityManager, dynamicEntityGraph, getDomainClass())
        );
        applyEntityGraphQueryHints(query, entityGraphHints);
        return query.getResultList();
    }

    @Override
    public List<T> findAll(@Nullable Specification<T> spec, JpaEntityGraph dynamicEntityGraph, Sort sort) {
        TypedQuery<T> query = getQuery(spec, sort);
        Map<String, Object> entityGraphHints = new HashMap<>(
                Jpa21Utils.tryGetFetchGraphHints(this.entityManager, dynamicEntityGraph, getDomainClass())
        );
        applyEntityGraphQueryHints(query, entityGraphHints);
        return query.getResultList();
    }

    @Override
    public Page<T> findAll(@Nullable Specification<T> spec, JpaEntityGraph dynamicEntityGraph, Pageable pageable) {
        TypedQuery<T> query = getQuery(spec, pageable);
        Map<String, Object> entityGraphHints = new HashMap<>(
                Jpa21Utils.tryGetFetchGraphHints(this.entityManager, dynamicEntityGraph, getDomainClass())
        );
        applyEntityGraphQueryHints(query, entityGraphHints);
        return (Page) (pageable.isUnpaged() ?
                new PageImpl(query.getResultList()) :
                readPage(query, getDomainClass(), pageable, spec)
        );
    }

    @Override
    public Page<T> findAll(Specification<T> spec, String namedEntityGraph, org.springframework.data.jpa.repository.EntityGraph.EntityGraphType type, Pageable pageable) {
        EntityGraph<?> namedGraph = this.entityManager.getEntityGraph(namedEntityGraph);
        TypedQuery<T> query = getQuery(spec, pageable);
        query.setHint(type.getKey(), namedGraph);
        return (Page) (pageable.isUnpaged() ?
                new PageImpl(query.getResultList()) :
                readPage(query, getDomainClass(), pageable, spec)
        );
    }

    @Override
    public Optional<T> findOne(@Nullable Specification<T> spec, JpaEntityGraph dynamicEntityGraph) {
        try {
            TypedQuery<T> query = this.getQuery(spec, Sort.unsorted());
            Map<String, Object> entityGraphHints = new HashMap<>(
                    Jpa21Utils.tryGetFetchGraphHints(this.entityManager, dynamicEntityGraph, getDomainClass())
            );
            applyEntityGraphQueryHints(query, entityGraphHints);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException var3) {
            return Optional.empty();
        }
    }


    private void applyEntityGraphQueryHints(Query query, Map<String, Object> hints) {
        for (Map.Entry<String, Object> hint : hints.entrySet()) {
            query.setHint(hint.getKey(), hint.getValue());
        }
    }
}
