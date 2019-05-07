package com.dynamic.graph.modules.users.service;

import com.dynamic.graph.modules.users.domain.User;
import com.dynamic.graph.modules.users.filter.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> findAll(UserFilter userFilter, Pageable pageable);
}
