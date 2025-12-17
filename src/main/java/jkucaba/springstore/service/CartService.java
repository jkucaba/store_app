package jkucaba.springstore.service;

import jkucaba.springstore.entity.Session;
import jkucaba.springstore.model.AddToCartRequest;
import jkucaba.springstore.model.CartItemResponse;
import jkucaba.springstore.model.ModifyCartItemRequest;

import java.util.List;
import java.util.UUID;

public interface CartService {
    void addItem(UUID sessionId, AddToCartRequest request);

    List<CartItemResponse> getCart(UUID sessionId);

    void modifyItem(UUID sessionId, ModifyCartItemRequest request);

    void removeItem(UUID sessionId, UUID cartItemId);

    void clearCart(Session session);
}
