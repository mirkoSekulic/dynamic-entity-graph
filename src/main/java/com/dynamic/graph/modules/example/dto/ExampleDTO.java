package com.dynamic.graph.modules.example.dto;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An ExampleDTO that represents Example
 */
@ApiModel(value = "Example")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ExampleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Min(1)
    private Long id;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String description;
}
