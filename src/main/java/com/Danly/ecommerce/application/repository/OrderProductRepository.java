package com.Danly.ecommerce.application.repository;

import com.Danly.ecommerce.domain.Order;
import com.Danly.ecommerce.domain.OrderProduct;

import java.util.List;

public interface OrderProductRepository {
    public OrderProduct create(OrderProduct orderProduct);
    public Iterable<OrderProduct> getOrderProducts();
    public List<OrderProduct> getOrdersProductByOrder(Order order);
}
