package jkucaba.springstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jkucaba.springstore.exception.ConflictException;
import jkucaba.springstore.exception.InvalidException;
import jkucaba.springstore.model.LoginRequest;
import jkucaba.springstore.model.LoginResponse;
import jkucaba.springstore.model.RegisterUserRequest;
import jkucaba.springstore.model.UserDTO;
import jkucaba.springstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String USER_PATH = "/api/v1/user";

    @Test
    void givenValidRegisterRequest_whenRegister_shouldReturnUserDTO() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("test@example.com", "password123");
        UserDTO userDTO = new UserDTO(UUID.randomUUID(), "test@example.com", Instant.now());

        given(userService.registerUser(any(RegisterUserRequest.class))).willReturn(userDTO);

        mockMvc.perform(post(USER_PATH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.id().toString()))
                .andExpect(jsonPath("$.email").value(userDTO.email()));
    }

    @Test
    void givenExistingEmail_whenRegister_shouldRespondWithConflict() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("existing@example.com", "password123");

        given(userService.registerUser(any(RegisterUserRequest.class)))
                .willThrow(new ConflictException("User already exists"));

        mockMvc.perform(post(USER_PATH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void givenInvalidRequestData_whenRegister_shouldRespondWithBadRequest() throws Exception {
        RegisterUserRequest invalidRequest = new RegisterUserRequest("", "");

        mockMvc.perform(post(USER_PATH + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidCredentials_whenLogin_shouldReturnSessionId() throws Exception {
        LoginRequest request = new LoginRequest("test@example.com", "password123");
        UUID sessionId = UUID.randomUUID();
        LoginResponse response = new LoginResponse(sessionId);

        given(userService.login(any(LoginRequest.class))).willReturn(response);

        mockMvc.perform(post(USER_PATH + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value(sessionId.toString()));
    }

    @Test
    void givenInvalidCredentials_whenLogin_shouldRespondWithError() throws Exception {
        LoginRequest request = new LoginRequest("wrong@example.com", "wrongpass");

        given(userService.login(any(LoginRequest.class)))
                .willThrow(new InvalidException("Invalid credentials"));

        mockMvc.perform(post(USER_PATH + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }
}