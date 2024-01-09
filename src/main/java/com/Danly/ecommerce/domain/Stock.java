package com.Danly.ecommerce.domain;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;


@Data //Anotacion para los getter y setter
@ToString //impresion de la informacion del objeto
public class Stock {

    private Integer id;
    private LocalDateTime dateCreated;
    private Integer unitIn;
    private Integer unitOut;
    private String description;
    private Integer balance;
    private Product product;
}
