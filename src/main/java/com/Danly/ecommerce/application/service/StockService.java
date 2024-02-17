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

    //Obteniendo el stock total en un solo objeto para ser comparado con la cantidad solicitada en el CartController
    public Stock getTotalStockByProduct(Product product){
        List<Stock> stockList = stockRepository.getStockByProduct(product);
        int totalBalance = stockList.stream().mapToInt(Stock::getBalance).sum();
        Stock totalStock = new Stock();
        totalStock.setBalance(totalBalance);
        return totalStock;
    }

}
