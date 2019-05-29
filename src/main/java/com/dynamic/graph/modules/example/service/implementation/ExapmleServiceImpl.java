package com.dynamic.graph.modules.example.service.implementation;


import com.dynamic.graph.modules.example.domain.Example;
import com.dynamic.graph.modules.example.filter.ExampleFilter;
import com.dynamic.graph.modules.example.predicate.ExamplePredicateFactory;
import com.dynamic.graph.modules.example.repository.ExampleRepository;
import com.dynamic.graph.modules.example.service.ExampleService;
import com.dynamic.graph.modules.shared.error.exception.ExampleNotFoundException;
import com.dynamic.graph.modules.shared.error.exception.entity.EntityMustHaveIDException;
import com.dynamic.graph.modules.shared.error.exception.entity.NewEntityCannotHaveIDException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ExapmleServiceImpl implements ExampleService {

    private final ExampleRepository exampleRepository;
    private final static String EXAMPLE_SINGLE = "example-single";

    public ExapmleServiceImpl(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Override
    public Page<Example> findAll(ExampleFilter exampleFilter, Pageable pageable, Boolean eager) {
        return exampleRepository.findAll(ExamplePredicateFactory.getExampleFilterPredicate(exampleFilter), pageable);
    }

    @Override
    public Example findById(Long id) {
        return exampleRepository.findById(id).orElseThrow(() ->
                new ExampleNotFoundException(id));
    }

    @Override
    public Example create(Example example) {
        if (example.getId() != null) {
            throw new NewEntityCannotHaveIDException("Example");
        }
        Example forInsert = new Example();
        BeanUtils.copyProperties(example, forInsert);
        forInsert.setCreatedDate(Instant.now());

        return exampleRepository.save(forInsert);
    }

    @Override
    public Example update(Example example) {
        if (example.getId() == null) {
            throw new EntityMustHaveIDException("Example");
        }

        Example fromDb = findById(example.getId());
        Example forUpdate = new Example();
        BeanUtils.copyProperties(example, forUpdate);
        forUpdate.setCreatedDate(fromDb.getCreatedDate());

        return exampleRepository.save(forUpdate);
    }

    @Override
    public void delete(Long id) {
        Example example = findById(id);
        exampleRepository.delete(example);
    }
}
