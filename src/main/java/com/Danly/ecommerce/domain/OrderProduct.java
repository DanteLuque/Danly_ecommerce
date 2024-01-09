package com.Danly.ecommerce.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProduct {
    private Product product; //el producto ordenado
    private Integer quantity; //Cantidad de productos
    private Order order; //Todos los productos ordenandos en una sola variable

    public OrderProduct(Product product, Integer quantity, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    public BigDecimal getTotalPrice(){
        //product tiene un atributo Price de tipo BigDecimal, un tipo de dato que contiene metodos que nos permiten hacer operaciones matematicas
        return this.product.getPrice().multiply(BigDecimal.valueOf(quantity)); //Aplicando casteo al quantity ya que es integer, y ahora lo multiplicamos
    }
}
