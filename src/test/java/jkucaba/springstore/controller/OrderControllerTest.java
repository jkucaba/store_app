package jkucaba.springstore.controller;

import jkucaba.springstore.entity.User;
import jkucaba.springstore.service.OrderService;
import jkucaba.springstore.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderService orderService;

    @MockitoBean
    SessionService sessionService;

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Test
    void givenValidRequest_whenCheckout_shouldReturnOrderDTO() throws Exception {
        given(orderService.checkout(any()))
                .willReturn(null);

        mockMvc.perform(post(ORDERS_PATH + "/checkout")
                        .header("X-SESSION-ID", "123e4567-e89b-12d3-a456-426614174000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void givenValidRequest_whenGetUserOrders_shouldReturnOrderList() throws Exception {
        given(orderService.getUserOrders(any()))
                .willReturn(null);
        given(sessionService.validateSession(any()))
                .willReturn(User.builder()
                        .id(UUID.randomUUID())
                        .email("mail@gmail.com")
                        .passwordHash("password")
                        .createdAt(Instant.now()).build());

        mockMvc.perform(get(ORDERS_PATH)
                        .header("X-SESSION-ID", "123e4567-e89b-12d3-a456-426614174000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

}