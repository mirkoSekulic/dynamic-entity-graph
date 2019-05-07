package com.dynamic.graph.custom_executor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface JpaSpecificationExecutorWithEntityGraph<T> extends JpaSpecificationExecutor<T> {
    Optional<T> findOne(@Nullable Specification<T> spec, JpaEntityGraph dynamicEntityGraph);

    List<T> findAll(JpaEntityGraph dynamicEntityGraph);

    List<T> findAll(JpaEntityGraph dynamicEntityGraph, Sort sort);

    List<T> findAll(Specification<T> spec, JpaEntityGraph dynamicEntityGraph, Sort sort);

    Page<T> findAll(Specification<T> spec, JpaEntityGraph dynamicEntityGraph, Pageable pageable);

    Page<T> findAll(Specification<T> spec, String namedEntityGraph, EntityGraph.EntityGraphType type, Pageable pageable);
}
