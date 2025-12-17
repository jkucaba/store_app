package jkucaba.springstore.repository;

import jkucaba.springstore.entity.CartItem;
import jkucaba.springstore.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<CartItem, UUID> {

    List<CartItem> findAllBySession(Session session);

    Optional<CartItem> findBySessionIdAndProductId(UUID sessionId, UUID productId);

    void deleteBySession(Session session);

}
