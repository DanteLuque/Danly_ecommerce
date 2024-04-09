package com.Danly.ecommerce.infrastructure.controller;
import com.Danly.ecommerce.application.service.ProductService;
import com.Danly.ecommerce.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class ImageController {

    @Autowired
    private ProductService productService; // Asegúrate de tener este servicio que te permita acceder a los datos del producto

    /*
    @GetMapping("/product-image/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        byte[] imageData = product.getImage();

        if (imageData != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_JPEG) // O el MediaType correspondiente al formato de tu imagen
                    .body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @GetMapping("/product-image/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer productId) {
        try {
            Product product = productService.getProductById(productId);
            byte[] imageData = product.getImage();

            if (imageData == null || imageData.length == 0) {
                // Cargar la imagen por defecto desde el classpath
                ClassPathResource imgFile = new ClassPathResource("static/images/default.jpg");
                byte[] defaultImageData = StreamUtils.copyToByteArray(imgFile.getInputStream());
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(defaultImageData);
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageData);
            }
        } catch (IOException e) {
            // Manejar la excepción, posiblemente registrando el error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
