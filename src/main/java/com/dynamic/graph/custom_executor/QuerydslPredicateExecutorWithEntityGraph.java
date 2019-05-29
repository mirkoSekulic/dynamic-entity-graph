package com.dynamic.graph.custom_executor;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QuerydslPredicateExecutorWithEntityGraph<T> extends QuerydslPredicateExecutor<T> {

    Page<T> findAll(Predicate var1, JpaEntityGraph dynamicEntityGraph, Pageable var2);
}
