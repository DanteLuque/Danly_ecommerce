package com.Danly.ecommerce.infrastructure.mapper;


import com.Danly.ecommerce.domain.Order;
import com.Danly.ecommerce.infrastructure.entity.OrderEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OrderMapper {

    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "dateCreated", target = "dateCreated"),
                    @Mapping(source = "user", target = "user")
            }
    )

    //Se crean primero los metodos
    Order toOrder(OrderEntity orderEntity); //solo necesitamos la inversa de este
    Iterable<Order> toOrders(Iterable<OrderEntity> orderEntities);
    @InheritInverseConfiguration
    OrderEntity toOrderEntity(Order order);
}
