package com.dynamic.graph.modules.example.controller;

import com.dynamic.graph.modules.example.domain.Example;
import com.dynamic.graph.modules.example.dto.ExampleDTO;
import com.dynamic.graph.modules.example.filter.ExampleFilter;
import com.dynamic.graph.modules.example.mapper.ExampleMapper;
import com.dynamic.graph.modules.example.service.ExampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "examples", tags = {"examples"})
@RequestMapping("examples")
public class ExampleController {
    private final ExampleService exampleService;
    private final ExampleMapper exampleMapper;

    public ExampleController(ExampleService exampleService, ExampleMapper exampleMapper) {
        this.exampleService = exampleService;
        this.exampleMapper = exampleMapper;
    }

    @GetMapping
    @ApiOperation(value = "Get a page of examples..", nickname = "findAll")
    public ResponseEntity<Page<ExampleDTO>> list(Pageable pageable, ExampleFilter exampleFilter, @RequestParam(value = "eager", required = false) Boolean eager) {
        Page<ExampleDTO> exampleDTOPage = exampleService.findAll(exampleFilter, pageable, eager)
                .map(exampleMapper::exampleToExampleDTO);

        return new ResponseEntity<>(exampleDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an example", nickname = "findById")
    public ResponseEntity<ExampleDTO> findById(@PathVariable("id") Long id) {
        Example createdExample = exampleService.findById(id);

        return new ResponseEntity<>(exampleMapper.exampleToExampleDTO(createdExample), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create an example", nickname = "create")
    public ResponseEntity<ExampleDTO> create(@RequestBody @Valid ExampleDTO exampleDTO) {
        Example createdExample = exampleService.create(
                exampleMapper.exampleDTOToExample(exampleDTO)
        );
        return new ResponseEntity<>(exampleMapper.exampleToExampleDTO(createdExample), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Update an example", nickname = "update")
    public ResponseEntity<ExampleDTO> update(@RequestBody @Valid ExampleDTO exampleDTO) {
        Example createdExample = exampleService.update(
                exampleMapper.exampleDTOToExample(exampleDTO)
        );
        return new ResponseEntity<>(exampleMapper.exampleToExampleDTO(createdExample), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an example", nickname = "delete")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        exampleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
