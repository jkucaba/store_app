package jkucaba.springstore.controller;

import jakarta.validation.Valid;
import jkucaba.springstore.model.RegisterUserRequest;
import jkucaba.springstore.model.UserDTO;
import jkucaba.springstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final String USER_PATH = "/api/v1/user";


    @PostMapping(USER_PATH + "/register")
    public ResponseEntity<UserDTO> register(
            @Valid @RequestBody RegisterUserRequest request) {

        UserDTO user = userService.registerUser(request);

        return ResponseEntity.ok(user);
    }
}
