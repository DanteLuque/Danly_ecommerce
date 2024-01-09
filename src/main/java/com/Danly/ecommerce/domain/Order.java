package com.Danly.ecommerce.domain;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Integer id;
    private LocalDateTime dateCreated;
    private List<OrderProduct> orderProducts; //Lista que nos almacene todas las ordenes realizadas
    private User user;

    public void addOrdersProduct(List<OrderProduct> orderProducts){
        this.setOrderProducts(orderProducts); //Estableciendo o agregando un OrderProduct
    }

    //Suma total de todos los totales resultantes de cada lista de ordenes
    /*
    .stream(): Convierte la lista de productos en un flujo (stream) de elementos.
    Esto permite realizar operaciones sobre cada elemento de la lista de manera secuencial o paralela.
    */
    public BigDecimal getTotalOrderPrice(){
        return getOrderProducts().stream().map( //mapeando cada orden
                p -> p.getTotalPrice() //Obteniendo el precio total de cada orden, representado por una variable "p"
        ).reduce(BigDecimal.ZERO, BigDecimal::add); //Reduciendo todos los valores en un solo valor, iniciando en CERO y se seguirá "add => agregando"
    }
}
