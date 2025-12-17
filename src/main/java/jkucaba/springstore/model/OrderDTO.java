package jkucaba.springstore.model;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDTO(
        UUID id,
        String status,
        BigDecimal total,
        String createdAt
) {
}
