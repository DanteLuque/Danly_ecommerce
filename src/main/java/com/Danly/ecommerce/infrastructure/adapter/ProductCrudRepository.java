package com.Danly.ecommerce.infrastructure.adapter;

import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.infrastructure.entity.ProductEntity;
import com.Danly.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository  extends CrudRepository<ProductEntity, Integer> {
    Iterable<ProductEntity> findByUserEntity (UserEntity userEntity);
}
