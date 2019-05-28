package com.dynamic.graph.modules.users.mapper;

import com.dynamic.graph.modules.shared.util.MapperUtil;
import com.dynamic.graph.modules.users.domain.User;
import com.dynamic.graph.modules.users.dto.UserDTO;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @BeforeMapping
    default void avoidLazyLoadSet(User user){
        if (!MapperUtil.wasInitialized(user.getAuthorities())) {
            user.setAuthorities(new HashSet<>());
        }
    }

    UserDTO userToUserDTO(User user);
}
