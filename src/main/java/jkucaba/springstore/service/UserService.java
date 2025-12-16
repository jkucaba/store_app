package jkucaba.springstore.service;

import jkucaba.springstore.model.RegisterUserRequest;
import jkucaba.springstore.model.UserDTO;

public interface UserService {
    UserDTO registerUser(RegisterUserRequest request);
}
