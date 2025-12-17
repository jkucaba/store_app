package jkucaba.springstore.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse (
        UUID id,
        String productTitle,
        int quantity,
        BigDecimal price,
        BigDecimal subtotal
){
}
