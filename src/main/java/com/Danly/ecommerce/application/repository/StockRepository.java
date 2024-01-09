package com.Danly.ecommerce.application.repository;

import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.Stock;

import java.util.List;

public interface StockRepository {

    Stock saveStock(Stock stock);
    List<Stock> getStockByProduct(Product product);
}
