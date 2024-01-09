package com.Danly.ecommerce.infrastructure.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable //permite usar esta clase como parte de la estructura de otras entidades
public class OrderProductPK {
    /*
    FetchType.LAZY: Con esta configuración,
    los datos relacionados (OrderEntity y ProductEntity)
    no se cargarán inmediatamente cuando se obtiene una instancia de OrderProductPK.
    Se cargarán solo cuando específicamente se acceda a esas entidades relacionadas.
    */
    @ManyToOne (fetch = FetchType.LAZY)
    private OrderEntity orderEntity;
    @ManyToOne (fetch = FetchType.LAZY)
    private ProductEntity productEntity;
}
