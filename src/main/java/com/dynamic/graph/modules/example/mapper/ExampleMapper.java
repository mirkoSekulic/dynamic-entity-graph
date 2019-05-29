package com.dynamic.graph.modules.example.mapper;

import com.dynamic.graph.modules.example.domain.Example;
import com.dynamic.graph.modules.example.dto.ExampleDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ExampleMapper {
    ExampleDTO exampleToExampleDTO(Example example);

    Example exampleDTOToExample(ExampleDTO exampleDTO);
}
