package com.Danly.ecommerce.infrastructure.adapter;

import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.infrastructure.entity.ProductEntity;
import com.Danly.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductCrudRepository  extends CrudRepository<ProductEntity, Integer> {
    Iterable<ProductEntity> findByUserEntity (UserEntity userEntity);
    // Este método buscará productos cuyo nombre contenga el string proporcionado, ignorando mayúsculas y minúsculas
    List<ProductEntity> findByNameContainingIgnoreCase(String name);
}
