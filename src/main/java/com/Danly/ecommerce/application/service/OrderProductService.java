package com.Danly.ecommerce.application.service;

import com.Danly.ecommerce.application.repository.OrderProductRepository;
import com.Danly.ecommerce.domain.Order;
import com.Danly.ecommerce.domain.OrderProduct;

import java.util.List;

public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public OrderProduct create(OrderProduct orderProduct){
        return  orderProductRepository.create(orderProduct);
    }

    public Iterable<OrderProduct> getOrderProduct(){
        return orderProductRepository.getOrderProducts();
    }

    public List<OrderProduct> getOrdersProductsByOrder(Order order){
        return orderProductRepository.getOrdersProductByOrder(order);
    }
}
