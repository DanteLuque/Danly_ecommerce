package com.Danly.ecommerce.infrastructure.mapper;

import com.Danly.ecommerce.domain.Stock;
import com.Danly.ecommerce.infrastructure.entity.ProductEntity;
import com.Danly.ecommerce.infrastructure.entity.StockEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

//Indicando que será un Mapper
@Mapper(componentModel = "spring", uses = {ProductMapper.class})//Le decimos que usaremos el mapeo de producos para pasar productEntity a Product
public interface StockMapper {

    /*
     Asi funciona:
     todo lo que está en "SOURCE" son los atributos de StockEntity (la clase dentro de infrastructure/entity)
     todo lo que está en "TARGET" son los atributos de Stock (la clase dentro de domain)
     De esta manera se hace un mapeo para trabajar en conjunto
    */
    @Mappings({
            @Mapping(source = "id", target = "id"),
                @Mapping(source = "dateCreated", target = "dateCreated"),
                @Mapping(source = "unitIn", target = "unitIn"),
                @Mapping(source = "unitOut", target = "unitOut"),
                @Mapping(source = "description", target = "description"),
                @Mapping(source = "balance", target = "balance"),
                @Mapping(source = "productEntity", target = "product") //productEntity va a pasar a Stock como product
    }
    )
    //metodo para pasar de StockEntity a Stock
    //target      source => siempre como parametro
    Stock toStock(StockEntity stockEntity); //le indicamos que aplicaremos un obejto Stock en conjunto con la entidad stockEntity

    //Pasar un listado de StockEntity a un listado de Stock
    List<Stock> toStocks(List<StockEntity> productEntities);


    //Esta animacion permite hacer exactamente el mismo metodo de arriba pero inverso
    //Haremos que StockEntity pase a ser Stock, esto para ahorrar codigo
    @InheritInverseConfiguration
    StockEntity toStockEntity(Stock stock);
}
