package org.mycompany.servimark.order.mapper;

import javax.annotation.processing.Generated;
import org.mycompany.servimark.order.OrderDTO;
import org.mycompany.servimark.order.model.Order;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-06T10:15:17-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Private Build)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order orderDTOToOrder(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        return order;
    }

    @Override
    public OrderDTO orderToOrderDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        return orderDTO;
    }
}
