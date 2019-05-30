package com.dynamic.graph.modules.example.service.implementation;


import com.dynamic.graph.modules.example.domain.Example;
import com.dynamic.graph.modules.example.domain.Example_;
import com.dynamic.graph.modules.example.filter.ExampleFilter;
import com.dynamic.graph.modules.example.predicate.ExamplePredicateFactory;
import com.dynamic.graph.modules.example.repository.ExampleRepository;
import com.dynamic.graph.modules.example.service.ExampleService;
import com.dynamic.graph.modules.shared.error.exception.ExampleNotFoundException;
import com.dynamic.graph.modules.shared.util.EntityGraphUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.stereotype.Service;

@Service
public class ExapmleServiceImpl implements ExampleService {

    private final ExampleRepository exampleRepository;

    public ExapmleServiceImpl(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public Page<Example> findAll(ExampleFilter exampleFilter, Pageable pageable, Boolean eager) {
        JpaEntityGraph dynamicGraph = eager != null && eager ? EntityGraphUtil.getDynamicGraph(
                "Example.exampleDetails",
                EntityGraph.EntityGraphType.FETCH,
                new String[]{Example_.EXAMPLE_DETAILS}
        ) : null;

        return exampleRepository.findAll(ExamplePredicateFactory.getExampleFilterPredicate(exampleFilter), dynamicGraph, pageable);
    }

    @Override
    public Example findById(Long id) {
        return exampleRepository.findById(id).orElseThrow(() ->
                new ExampleNotFoundException(id));
    }
}
