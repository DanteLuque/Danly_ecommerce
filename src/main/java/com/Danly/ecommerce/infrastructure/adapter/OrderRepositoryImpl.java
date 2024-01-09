package com.Danly.ecommerce.infrastructure.adapter;


import com.Danly.ecommerce.application.repository.OrderRepository;
import com.Danly.ecommerce.domain.Order;
import com.Danly.ecommerce.domain.User;
import com.Danly.ecommerce.infrastructure.mapper.OrderMapper;
import com.Danly.ecommerce.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderCrudRepository orderCrudRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public OrderRepositoryImpl(OrderCrudRepository orderCrudRepository, OrderMapper orderMapper, UserMapper userMapper) {
        this.orderCrudRepository = orderCrudRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Order createOrder(Order order) {
        //El objeto "order" lo convertimos a "OrderEntity" para que se pueda aplicar el CRUD de "OrderCrudRepository" y guardarlo
        //Luego de que se ha guardado, esto lo convertimos en un objeto de tipo "Order" por medio de "OrderMapper" y lo retornamos
        return orderMapper.toOrder(orderCrudRepository.save(orderMapper.toOrderEntity(order)));
    }

    @Override
    public Iterable<Order> getOrders() {
        return orderMapper.toOrders(orderCrudRepository.findAll()); //Devolviendo toda la lista de ordenes
    }

    @Override
    public Iterable<Order> getOrdersByUser(User user) {
        return orderMapper.toOrders(orderCrudRepository.findByUser(userMapper.toUserEntity(user))); //Devolviendo una lista de ordenes filtrada por usuario
    }
}
