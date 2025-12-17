package jkucaba.springstore.service;

import jakarta.transaction.Transactional;
import jkucaba.springstore.entity.CartItem;
import jkucaba.springstore.entity.Product;
import jkucaba.springstore.entity.Session;
import jkucaba.springstore.exception.InvalidException;
import jkucaba.springstore.exception.NotFoundException;
import jkucaba.springstore.mapper.CartMapper;
import jkucaba.springstore.model.AddToCartRequest;
import jkucaba.springstore.model.CartItemResponse;
import jkucaba.springstore.model.ModifyCartItemRequest;
import jkucaba.springstore.repository.CartRepository;
import jkucaba.springstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final SessionService sessionService;
    private final CartMapper cartMapper;

    @Override
    public void addItem(UUID sessionId, AddToCartRequest request) {
        Session session = sessionService.validateSessionEntity(sessionId);

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (product.getAvailable() < request.quantity()) {
            throw new InvalidException("Not enough stock");
        }

        cartItemRepository.findBySessionIdAndProductId(sessionId, product.getId())
                .ifPresent(item -> {
                    throw new InvalidException("Product already in cart");
                });

        CartItem item = CartItem.create(session, product, request.quantity());
        cartItemRepository.save(item);

    }

    @Override
    public List<CartItemResponse> getCart(UUID sessionId) {
        Session session = sessionService.validateSessionEntity(sessionId);

        return cartItemRepository.findAllBySession(session)
                .stream().map(
                        cartMapper::cartItemToCartItemResponse
                ).collect(Collectors.toList());

    }

    @Override
    public void modifyItem(UUID sessionId, ModifyCartItemRequest request) {
        sessionService.validateSession(sessionId);
        CartItem item = cartItemRepository.findById(request.cartItemId())
                .orElseThrow(() -> new NotFoundException("Cart item not found"));

        if (item.getProduct().getAvailable() < request.quantity()) {
            throw new InvalidException("Not enough stock");
        }

        item.changeQuantity(request.quantity());
    }

    @Override
    public void removeItem(UUID sessionId, UUID cartItemId) {
        sessionService.validateSession(sessionId);

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("Cart item not found"));

        cartItemRepository.delete(item);
    }

    @Override
    public void clearCart(Session session) {
        cartItemRepository.deleteBySession(session);
    }
}
