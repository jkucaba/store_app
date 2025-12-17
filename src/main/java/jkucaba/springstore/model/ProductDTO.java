package jkucaba.springstore.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(
        UUID id,
        String title,
        Integer available,
        BigDecimal price
) {
}
