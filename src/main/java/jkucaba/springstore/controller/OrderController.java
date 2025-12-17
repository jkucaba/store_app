package jkucaba.springstore.controller;

import jkucaba.springstore.model.OrderDTO;
import jkucaba.springstore.service.OrderService;
import jkucaba.springstore.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final SessionService sessionService;
    private static final String ORDERS_PATH = "/api/v1/orders";

    @PostMapping(ORDERS_PATH+"/checkout")
    public ResponseEntity<OrderDTO> checkout(
            @RequestHeader("X-SESSION-ID") UUID sessionId) {

        OrderDTO orderDTO = orderService.checkout(sessionId);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping(ORDERS_PATH)
    public ResponseEntity<List<OrderDTO>> getUserOrders(
            @RequestHeader("X-SESSION-ID") UUID sessionId) {
        List<OrderDTO> orders = orderService.getUserOrders(sessionService.validateSession(sessionId).getId());
        return ResponseEntity.ok(orders);
    }
}
