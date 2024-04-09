package com.Danly.ecommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString //anotacion que nos permite acceder a la informacion e imprimir el objeto desde otro lugar
public class Product {
    private Integer id;
    private  String code;
    private String name;
    private String description;
   // private String image;
    private byte[] image;
    private BigDecimal price;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    private User user;

    public Product() {
        this.setCode(UUID.randomUUID().toString());
    }
}
