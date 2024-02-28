/*
 la relación se gestiona a través de la tabla intermedia
 OrderProductEntity utilizando la clase OrderProductPK para
 definir su clave primaria compuesta, que consta de las referencias
 a OrderEntity y ProductEntity.
*/
package com.Danly.ecommerce.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "ordersproducts")
public class OrderProductEntity {
    /*
    La clase OrderProductPK (clave primaria compuesta)
    no es una entidad por sí misma, sino una representación
    de la clave primaria compuesta de la tabla intermedia
    OrderProductEntity. Esto se hace mediante la anotación
    @EmbeddedId, que indica que los atributos dentro de
    OrderProductPK forman la clave primaria de la tabla
    OrderProductEntity.
     */
    @EmbeddedId
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderProductPK pk;
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer quantity;

}

/*

Crear una clave primaria compuesta se utiliza cuando una tabla
necesita identificar de manera única sus filas utilizando más de
un campo. En el caso de tu estructura con OrderProductPK,
se utiliza una clave primaria compuesta porque la tabla intermedia
OrderProductEntity necesita identificar de forma única una relación
entre un pedido (OrderEntity) y un producto (ProductEntity).
*/
