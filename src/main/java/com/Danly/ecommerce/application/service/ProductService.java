package com.Danly.ecommerce.application.service;


import com.Danly.ecommerce.application.repository.ProductRepository;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProductService {

    //constructor
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    //metodos que inicializan o ejecutan los servicios establecidos en ProductRepository
    public Iterable<Product> getProducts(){ //Obtener toda la lista de productos
        return  productRepository.getProducts();
    }


    public Iterable<Product> getProductsByUser(User user){ //Obteniendo producto por usuario
        return productRepository.getProductsByUser(user);
    }

    public Product getProductById(Integer id){ //obteniendo producto por id
        return  productRepository.getProductById(id);
    }



    public Product saveProduct(Product product, MultipartFile multipartFile, HttpSession httpSession) throws IOException {
        User user = (product.getId() == null) ? new User() : productRepository.getProductById(product.getId()).getUser();
        user.setId(Integer.parseInt(httpSession.getAttribute("iduser").toString()));

        product.setUser(user);
        product.setDateUpdated(LocalDateTime.now());

        if (product.getId() == null) {
            product.setDateCreated(LocalDateTime.now());
        } else {
            product.setDateCreated(productRepository.getProductById(product.getId()).getDateCreated());
        }

        if (!multipartFile.isEmpty()) {
            product.setImage(multipartFile.getBytes());
        } else if (product.getId() != null) {
            product.setImage(productRepository.getProductById(product.getId()).getImage());
        }

        return productRepository.saveProduct(product);
    }
    public void deleteProductById(Integer id){ //eliminacion de producto por id
        productRepository.deleteProductById(id);
    }

    public Iterable<Product> findByNameContainingIgnoreCase(String name) {
        // Suponiendo que tienes un método en tu repositorio para buscar por nombre
        return productRepository.searchByName(name);
    }
}
