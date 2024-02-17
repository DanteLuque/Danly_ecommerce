package com.Danly.ecommerce.application.service;

import com.Danly.ecommerce.application.repository.StockRepository;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.Stock;

import java.util.List;

public class StockService {

    //Inyectando StockRepository en el constructor de StockService
    private final StockRepository stockRepository;
    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }


    //metodos
    public Stock saveStock(Stock stock){
        return stockRepository.saveStock(stock);
    }


    public List<Stock> getStockByProduct(Product product){
        return stockRepository.getStockByProduct(product);
    }


}
