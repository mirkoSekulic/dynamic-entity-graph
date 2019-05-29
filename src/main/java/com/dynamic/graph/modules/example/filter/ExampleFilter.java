package com.dynamic.graph.modules.example.filter;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ExampleFilter implements Serializable {

    private String name;
    private String description;
}
