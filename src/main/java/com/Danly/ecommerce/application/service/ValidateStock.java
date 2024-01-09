package com.Danly.ecommerce.application.service;

import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.Stock;

import java.util.List;

public class ValidateStock {
    private final StockService stockService;

    public ValidateStock(StockService stockService) {
        this.stockService = stockService;
    }


    private boolean existBalance(Product product){
        List<Stock> stockList = stockService.getStockByProduct(product);
        return stockList.isEmpty() ? false : true; //Si está vacia, retornamos false, caso contrario, retornamos true (quiere decir que si podemos gestionar el inventario del producto)
    }

    public Stock calculateBalance(Stock stock){
        //GESTIONANDO LA ENTRADA DE UNIDADES EN EL INVENTARIO
        if(stock.getUnitIn() != 0){ //Si el ingreso del producto es diferente de cero, vamos a agregar
            if(existBalance(stock.getProduct())){ //Si existen registros de inventario en un determinado producto
                List<Stock> stockList = stockService.getStockByProduct(stock.getProduct()); //Le pasamos la lista de stock de un producto
                Integer balance = stockList.get(stockList.size()-1).getBalance(); //Le pasamos el ultimo registro del sotck
                stock.setBalance(balance + stock.getUnitIn()); //actualizamos el nuevo valor para el balance
            } else{
                stock.setBalance(stock.getUnitIn()); //Si no existe ningun inventario, establecemos el nuevo valor de unidades a ingresar
            }
        }else{
            //CUANDO SEA VENTA, SE RESTARÁ LAS UNIDADES DEL INVENTARIO
            List<Stock> stockList = stockService.getStockByProduct(stock.getProduct()); //Le pasamos la lista de stock de un producto
            Integer balance = stockList.get(stockList.size()-1).getBalance(); //Le pasamos el ultimo registro del sotck
            stock.setBalance(balance - stock.getUnitOut()); //actualizamos el nuevo valor para el balance
        }
        return stock;
    }
}
