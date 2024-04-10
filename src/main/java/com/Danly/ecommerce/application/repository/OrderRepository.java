package com.Danly.ecommerce.application.repository;

import com.Danly.ecommerce.domain.Order;
import com.Danly.ecommerce.domain.User;

import java.util.Optional;

public interface OrderRepository {
    public Order createOrder(Order order);
    public Iterable<Order> getOrders();
    public Iterable<Order> getOrdersByUser(User user);
    Optional<Order> findByIdAndUser(Integer id, User user);
}
