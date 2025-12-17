package jkucaba.springstore.repository;

import jkucaba.springstore.entity.Order;
import jkucaba.springstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByUserOrderByCreatedAtDesc(User user);

}
