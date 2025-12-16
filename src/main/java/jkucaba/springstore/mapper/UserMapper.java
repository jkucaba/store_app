package jkucaba.springstore.mapper;

import jkucaba.springstore.entity.User;
import jkucaba.springstore.model.RegisterUserRequest;
import jkucaba.springstore.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    UserDTO userToUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User registerUserToUser(RegisterUserRequest request);
}
