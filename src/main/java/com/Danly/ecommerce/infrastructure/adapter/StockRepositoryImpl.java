package com.Danly.ecommerce.infrastructure.adapter;


import com.Danly.ecommerce.application.repository.StockRepository;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.Stock;
import com.Danly.ecommerce.infrastructure.mapper.ProductMapper;
import com.Danly.ecommerce.infrastructure.mapper.StockMapper;
import org.springframework.stereotype.Service;

import java.util.List;

//Implementando los repositorios dentro de adapter
@Service
public class StockRepositoryImpl implements StockRepository {

    private final StockCrudRepository stockCrudRepository;
    private final StockMapper stockMapper;
    private final ProductMapper productMapper;

    public StockRepositoryImpl(StockCrudRepository stockCrudRepository, StockMapper stockMapper, ProductMapper productMapper) {
        this.stockCrudRepository = stockCrudRepository;
        this.stockMapper = stockMapper;
        this.productMapper = productMapper;
    }

    @Override
    //Le pasamos un stock que será convertido a StockEntity por medio del mapeo, esto será guardado por medio de CRUD que se creó gracias a SpringDataJPA y por ultimo lo pasamos a stock y lo retornamos
    public Stock saveStock(Stock stock) {
        return stockMapper.toStock(stockCrudRepository.save(stockMapper.toStockEntity(stock))); //Lo convertimos ya que para usar el metodo save del CRUD, solo recibe StockEntity, asi que lo convetimos a Stock con el mapeo
    }

    @Override
    public List<Stock> getStockByProduct(Product product) {
        return stockMapper.toStocks(stockCrudRepository.findByProductEntity(productMapper.toProductEntity(product)));
    }


}
