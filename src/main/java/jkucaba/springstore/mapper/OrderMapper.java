package jkucaba.springstore.mapper;

import jkucaba.springstore.entity.Order;
import jkucaba.springstore.model.OrderDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    OrderDTO orderToOrderDTO(Order order);

    Order orderDTOToOrder(OrderDTO orderDTO);
}
