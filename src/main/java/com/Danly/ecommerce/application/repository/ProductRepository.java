package com.Danly.ecommerce.application.repository;

import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.User;
public interface ProductRepository {
    Iterable<Product> getProducts(); //metodo para obtener producto
    Iterable<Product> getProductsByUser(User user); //metodo para obtener producto por usuario
    Product getProductById(Integer id); //metodo para retornar producto por id
    Product saveProduct(Product product); //metodo para guardar producto
    void deleteProductById(Integer id); //metodo para eliminar un propducto
    Iterable<Product> searchByName(String name);
}
