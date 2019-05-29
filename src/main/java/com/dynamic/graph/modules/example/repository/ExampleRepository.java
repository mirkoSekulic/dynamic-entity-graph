package com.dynamic.graph.modules.example.repository;

import com.dynamic.graph.custom_executor.QuerydslPredicateExecutorWithEntityGraph;
import com.dynamic.graph.modules.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ExampleRepository extends JpaRepository<Example, Long>, QuerydslPredicateExecutorWithEntityGraph {
}
