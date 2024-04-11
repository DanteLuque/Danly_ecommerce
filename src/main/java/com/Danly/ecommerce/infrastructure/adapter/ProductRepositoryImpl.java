package com.Danly.ecommerce.infrastructure.adapter;

import com.Danly.ecommerce.application.repository.ProductRepository;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.User;
import com.Danly.ecommerce.infrastructure.entity.ProductEntity;
import com.Danly.ecommerce.infrastructure.mapper.ProductMapper;
import com.Danly.ecommerce.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductCrudRepository productCrudRepository;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    public ProductRepositoryImpl(ProductCrudRepository productCrudRepository, ProductMapper productMapper, UserMapper userMapper) {
        this.productCrudRepository = productCrudRepository;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Iterable<Product> getProducts() {
        return productMapper.toProducts(productCrudRepository.findAll());
    }

    @Override
    public Iterable<Product> getProductsByUser(User user) {
        return productMapper.toProducts(productCrudRepository.findByUserEntity(userMapper.toUserEntity(user)) );
    }

    @Override
    public Product getProductById(Integer id) {
        return productMapper.toProduct(productCrudRepository.findById(id).get());
    }

    @Override
    public Product saveProduct(Product product) {
        return productMapper.toProduct( productCrudRepository.save(productMapper.toProductEntity(product) ) );
    }

    @Override
    public void deleteProductById(Integer id) {
        productCrudRepository.deleteById(id);
    }

    @Override
    public Iterable<Product> searchByName(String name) {
        List<ProductEntity> productEntities = productCrudRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toProducts(productEntities);
    }
}
