package com.Danly.ecommerce.infrastructure.adapter;

import com.Danly.ecommerce.infrastructure.entity.ProductEntity;
import com.Danly.ecommerce.infrastructure.entity.StockEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


//Los CrudReposiroty son la parte del codigo donde declaramos que entitades van a tener un CRUD
public interface StockCrudRepository extends CrudRepository<StockEntity, Integer> { //le pasamos la entidad y el tipo de dato de la primary key

    //SpringDataJPA nos permite hacer filtros de manera automatica basados en los actributos de la clase utilizada, en este caso "StockEntity"

    List<StockEntity> findByProductEntity(ProductEntity productEntity); //buscando automaticamente en todos los stok por medio del atributo ProductEntity
}
