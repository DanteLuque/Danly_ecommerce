package com.Danly.ecommerce.infrastructure.entity;

import com.Danly.ecommerce.domain.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity //Indicamos que es una entidad
@Table(name = "stock") //Indicamos que será una tabla
//Los constructores sin argumentos nos ayuda en este caso a aplicar "serializacion"(convertir un objeto a una secuencia de bytes)
@NoArgsConstructor //Genera automaticamente un constructor sin argumentos
@Data //generar getter and setter
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //clave primaria autoincrementable
    private Integer id;
    private LocalDateTime dateCreated;
    private Integer unitIn;
    private Integer unitOut;
    private String description;
    private Integer balance;

    @ManyToOne //relacion de muchos a uno
    @OnDelete(action = OnDeleteAction.CASCADE) //cuando borremos un producto, tambien se eliminará todos sus inventarios
    private ProductEntity productEntity;
}
