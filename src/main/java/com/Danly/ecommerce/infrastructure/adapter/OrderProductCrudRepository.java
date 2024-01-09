package com.Danly.ecommerce.infrastructure.adapter;

import com.Danly.ecommerce.infrastructure.entity.OrderEntity;
import com.Danly.ecommerce.infrastructure.entity.OrderProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderProductCrudRepository extends CrudRepository<OrderProductEntity, Integer> {
    List<OrderProductEntity> findByPkOrderEntity(OrderEntity orderEntity);

}
