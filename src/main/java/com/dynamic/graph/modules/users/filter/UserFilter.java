package com.dynamic.graph.modules.users.filter;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserFilter implements Serializable {

    private String firstName;
    private String lastName;
    private List<String> roles;
}
