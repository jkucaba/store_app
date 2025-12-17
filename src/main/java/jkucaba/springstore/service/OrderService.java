package jkucaba.springstore.service;

import jkucaba.springstore.model.OrderDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDTO checkout(UUID sessionId);
    List<OrderDTO> getUserOrders(UUID userId);
}
