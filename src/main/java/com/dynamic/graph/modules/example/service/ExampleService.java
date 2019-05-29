package com.dynamic.graph.modules.example.service;

import com.dynamic.graph.modules.example.domain.Example;
import com.dynamic.graph.modules.example.filter.ExampleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExampleService {

    Page<Example> findAll(ExampleFilter exampleFilter, Pageable pageable, Boolean eager);

    Example create(Example example);

    Example update(Example example);

    void delete(Long id);

    Example findById(Long id);
}
