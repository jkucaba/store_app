package jkucaba.springstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jkucaba.springstore.exception.InvalidException;
import jkucaba.springstore.exception.NotFoundException;
import jkucaba.springstore.model.AddToCartRequest;
import jkucaba.springstore.model.CartItemResponse;
import jkucaba.springstore.model.ModifyCartItemRequest;
import jkucaba.springstore.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String CART_PATH = "/api/v1/cart";
    private static final String HEADER_SESSION_ID = "X-SESSION-ID";
    private static final UUID SESSION_ID = UUID.randomUUID();

    @Test
    void givenValidRequestAndHeader_whenAddToCart_shouldReturnOk() throws Exception {
        AddToCartRequest request = new AddToCartRequest(UUID.randomUUID(), 5);

        mockMvc.perform(post(CART_PATH)
                        .header(HEADER_SESSION_ID, SESSION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void givenMissingSessionHeader_whenAddToCart_shouldReturnBadRequest() throws Exception {
        AddToCartRequest request = new AddToCartRequest(UUID.randomUUID(), 1);

        mockMvc.perform(post(CART_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenProductNotFound_whenAddToCart_shouldReturnNotFound() throws Exception {
        AddToCartRequest request = new AddToCartRequest(UUID.randomUUID(), 1);

        willThrow(new NotFoundException("Product not found"))
                .given(cartService).addItem(eq(SESSION_ID), any(AddToCartRequest.class));

        mockMvc.perform(post(CART_PATH)
                        .header(HEADER_SESSION_ID, SESSION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNotEnoughStock_whenAddToCart_shouldReturnBadRequest() throws Exception {
        AddToCartRequest request = new AddToCartRequest(UUID.randomUUID(), 100);

        willThrow(new InvalidException("Not enough stock"))
                .given(cartService).addItem(eq(SESSION_ID), any(AddToCartRequest.class));

        mockMvc.perform(post(CART_PATH)
                        .header(HEADER_SESSION_ID, SESSION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void givenValidSession_whenGetCart_shouldReturnList() throws Exception {
        CartItemResponse item1 = new CartItemResponse(
                UUID.randomUUID(), "Product A", 2, BigDecimal.TEN, BigDecimal.valueOf(20));
        CartItemResponse item2 = new CartItemResponse(
                UUID.randomUUID(), "Product B", 1, BigDecimal.ONE, BigDecimal.ONE);

        List<CartItemResponse> mockResponse = List.of(item1, item2);

        given(cartService.getCart(SESSION_ID)).willReturn(mockResponse);

        mockMvc.perform(get(CART_PATH)
                        .header(HEADER_SESSION_ID, SESSION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productTitle").value("Product A"))
                .andExpect(jsonPath("$[0].subtotal").value(20));
    }

    @Test
    void givenValidModification_whenModifyCartItem_shouldReturnOk() throws Exception {
        ModifyCartItemRequest request = new ModifyCartItemRequest(UUID.randomUUID(), 3);

        mockMvc.perform(put(CART_PATH)
                        .header(HEADER_SESSION_ID, SESSION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void givenValidItemId_whenRemoveCartItem_shouldReturnOk() throws Exception {
        UUID cartItemId = UUID.randomUUID();

        mockMvc.perform(delete(CART_PATH + "/" + cartItemId)
                        .header(HEADER_SESSION_ID, SESSION_ID))
                .andExpect(status().isOk());
    }
}