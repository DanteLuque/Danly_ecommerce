package com.Danly.ecommerce.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private  String code;
    private String name;
    private String description;


   // private String image;

    @Lob  // Indica que este campo es un objeto grande (LOB)
    @Column(columnDefinition = "MEDIUMBLOB") // Esto es para MySQL, ajusta según tu DB si es necesario
    private byte[] image;  // Cambiado de String a byte[] para almacenar la imagen


    private BigDecimal price;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    @ManyToOne //relacion de uno a muchos
    private UserEntity userEntity;
}
