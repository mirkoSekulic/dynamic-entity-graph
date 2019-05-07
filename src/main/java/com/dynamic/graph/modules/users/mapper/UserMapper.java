package com.dynamic.graph.modules.users.mapper;

import com.dynamic.graph.modules.users.domain.User;
import com.dynamic.graph.modules.users.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @Mapping(target = "authorities", ignore = true)
    UserDTO userToUserDTO(User user);
}
