package com.dynamic.graph.modules.example.mapper;

import com.dynamic.graph.modules.example.domain.Example;
import com.dynamic.graph.modules.example.dto.ExampleDTO;
import com.dynamic.graph.modules.shared.util.MapperUtil;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Mapper(componentModel = "spring")
@Component
public interface ExampleMapper {
    ExampleDTO exampleToExampleDTO(Example example);

    Example exampleDTOToExample(ExampleDTO exampleDTO);

    @BeforeMapping
    default void avoidLazyLoadSet(Example example){
        if (!MapperUtil.wasInitialized(example.getExampleDetails())) {
            example.setExampleDetails(new HashSet<>());
        }
    }
}
