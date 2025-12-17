package jkucaba.springstore.service;

import jkucaba.springstore.entity.User;
import jkucaba.springstore.model.LoginRequest;
import jkucaba.springstore.model.LoginResponse;
import jkucaba.springstore.model.RegisterUserRequest;
import jkucaba.springstore.model.UserDTO;

import java.util.UUID;

public interface UserService
{
    UserDTO registerUser(RegisterUserRequest request);

    LoginResponse login(LoginRequest loginRequest);

    User getUserById(UUID userId);
}
