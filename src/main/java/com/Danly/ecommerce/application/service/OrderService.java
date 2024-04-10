//EN LOS SERVICIOS IMPLEMENTAMOS LOS METODOS DEL REPOSITORIO
package com.Danly.ecommerce.application.service;

import com.Danly.ecommerce.application.repository.OrderRepository;
import com.Danly.ecommerce.domain.Order;
import com.Danly.ecommerce.domain.User;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //creando una orden
    public Order createOrder(Order order){
        return orderRepository.createOrder(order);
    }

    //retornando toda la lista de ordenes
    public Iterable<Order> getOrders(){
        return orderRepository.getOrders();
    }

    public Iterable<Order> getOrdersByUser(User user){
        return orderRepository.getOrdersByUser(user); //Implementamos o inicializamos el metodo
    }
    public Order getOrderByIdAndUser(Integer id, User user) {
        return orderRepository.findByIdAndUser(id, user).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
}
