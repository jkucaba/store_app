package jkucaba.springstore.service;

import jakarta.transaction.Transactional;
import jkucaba.springstore.entity.*;
import jkucaba.springstore.exception.InvalidException;
import jkucaba.springstore.mapper.OrderMapper;
import jkucaba.springstore.model.OrderDTO;
import jkucaba.springstore.repository.CartRepository;
import jkucaba.springstore.repository.OrderRepository;
import jkucaba.springstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final SessionService sessionService;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO checkout(UUID sessionId) {
        Session session = sessionService.validateSessionEntity(sessionId);
        List<CartItem> cartItems = cartRepository.findAllBySession(session);
        if(cartItems.isEmpty()) {
            throw new InvalidException("Cart is empty");
        }

        for(CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new InvalidException("Product not found: " + item.getProduct().getTitle()));

            if(product.getAvailable() < item.getQuantity()) {
                throw new InvalidException("Not enough stock for product: " + item.getProduct().getTitle());
            }

            product.setAvailable(product.getAvailable() - item.getQuantity());
        }

        Order order = Order.builder()
                .user(session.getUser())
                .status(OrderStatus.CREATED)
                .total(cartItems.stream()
                        .map(ci -> ci.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(ci.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .createdAt(Instant.now())
                .build();

        List<OrderItem> orderItems = cartItems.stream().map(ci ->
                OrderItem.builder()
                        .order(order)
                        .product(ci.getProduct())
                        .quantity(ci.getQuantity())
                        .priceAtPurchase(ci.getProduct().getPrice())
                        .build()
        ).toList();

        order.setItems(orderItems);

        orderRepository.save(order);
        cartRepository.deleteBySession(session);

        return orderMapper.orderToOrderDTO(order);

    }

    @Override
    public List<OrderDTO> getUserOrders(UUID userId) {
        return orderRepository.findAllByUserOrderByCreatedAtDesc(
                userService.getUserById(userId)
        ).stream()
                .map(orderMapper::orderToOrderDTO)
                .toList();
    }
}
