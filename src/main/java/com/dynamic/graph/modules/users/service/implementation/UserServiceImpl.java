package com.dynamic.graph.modules.users.service.implementation;

import com.dynamic.graph.modules.shared.util.EntityGraphUtil;
import com.dynamic.graph.modules.users.domain.User;
import com.dynamic.graph.modules.users.domain.User_;
import com.dynamic.graph.modules.users.filter.UserFilter;
import com.dynamic.graph.modules.users.repository.UserRepository;
import com.dynamic.graph.modules.users.service.UserService;
import com.dynamic.graph.modules.users.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> findAll(@NotNull UserFilter userFilter, Pageable pageable, Boolean eager) {
        JpaEntityGraph dynamicGraph = eager != null && eager ? EntityGraphUtil.getDynamicGraph(
                "user",
                EntityGraph.EntityGraphType.FETCH,
                new String[]{User_.AUTHORITIES}
        ) : null;
        return userRepository.findAll(UserSpecification.of(userFilter), dynamicGraph, pageable);
    }
}
