package jkucaba.springstore.controller;

import jakarta.validation.Valid;
import jkucaba.springstore.model.AddToCartRequest;
import jkucaba.springstore.model.CartItemResponse;
import jkucaba.springstore.model.ModifyCartItemRequest;
import jkucaba.springstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private static final String CART_PATH = "/api/v1/cart";

    @PostMapping(CART_PATH)
    public ResponseEntity<Void> addToCart(
            @RequestHeader("X-SESSION-ID") UUID sessionId,
            @Valid @RequestBody AddToCartRequest request) {

        cartService.addItem(sessionId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(CART_PATH)
    public List<CartItemResponse> getCart(
            @RequestHeader("X-SESSION-ID") UUID sessionId) {

        return cartService.getCart(sessionId);
    }

    @PutMapping(CART_PATH)
    public ResponseEntity<Void> modifyCartItem(
            @RequestHeader("X-SESSION-ID") UUID sessionId,
            @Valid @RequestBody ModifyCartItemRequest request) {

        cartService.modifyItem(sessionId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(CART_PATH + "/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @RequestHeader("X-SESSION-ID") UUID sessionId,
            @PathVariable UUID cartItemId) {

        cartService.removeItem(sessionId, cartItemId);
        return ResponseEntity.ok().build();
    }
}
