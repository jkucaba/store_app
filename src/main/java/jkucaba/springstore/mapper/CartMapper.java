package jkucaba.springstore.mapper;

import jkucaba.springstore.entity.CartItem;
import jkucaba.springstore.model.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper
public interface CartMapper {

    @Mapping(target = "productTitle", source = "product.title")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "subtotal", expression = "java(subtotal(cartItem))")
    CartItemResponse cartItemToCartItemResponse(CartItem cartItem);

    default BigDecimal subtotal(CartItem item) {
        return item.getProduct()
                .getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
    }
}
