package jkucaba.springstore.service;

import jkucaba.springstore.entity.User;
import jkucaba.springstore.exception.EmailAlreadyExistsException;
import jkucaba.springstore.mapper.UserMapper;
import jkucaba.springstore.model.RegisterUserRequest;
import jkucaba.springstore.model.UserDTO;
import jkucaba.springstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDTO registerUser(RegisterUserRequest request) {

        if(userRepository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = userMapper.registerUserToUser(request);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        return userMapper.userToUserDTO(userRepository.save(user));

    }
}
