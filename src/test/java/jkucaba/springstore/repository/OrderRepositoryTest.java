package jkucaba.springstore.repository;

import jkucaba.springstore.entity.Order;
import jkucaba.springstore.entity.OrderStatus;
import jkucaba.springstore.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("jk@gmail.com")
                .passwordHash("hashedpassword")
                .build();
        userRepository.save(user);
        userRepository.flush();

    }

    @Test
    void saveOrder_shouldFlushOrderToRepository() {
        Order savedOrder = orderRepository.save(Order.builder()
                .user(user)
                .total(BigDecimal.valueOf(10))
                .status(OrderStatus.CREATED)
                .items(new ArrayList<>())
                .build());
        orderRepository.flush();

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getUser()).isEqualTo(user);
        assertThat(savedOrder.getTotal()).isEqualByComparingTo(BigDecimal.valueOf(10));
        assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    void saveOrderWithoutUser_shouldThrowException() {

        assertThrows(DataIntegrityViolationException.class, () -> {
            Order order = Order.builder()
                    .total(BigDecimal.valueOf(10))
                    .status(OrderStatus.CREATED)
                    .items(new ArrayList<>())
                    .build();
            orderRepository.save(order);
            orderRepository.flush();
        });
    }
}