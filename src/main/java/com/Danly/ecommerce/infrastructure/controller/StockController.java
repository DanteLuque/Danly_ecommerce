package com.Danly.ecommerce.infrastructure.controller;

import com.Danly.ecommerce.application.service.StockService;
import com.Danly.ecommerce.application.service.ValidateStock;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.Stock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/products/stock")
public class StockController{

    private final StockService stockService;
    private final ValidateStock validateStock;

    public StockController(StockService stockService, ValidateStock validateStock) {
        this.stockService = stockService;
        this.validateStock = validateStock;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model){ //Le pasamos la variable que se encontrará en la url
        Product product = new Product(); //Creando un nuevo producto para que sea obtenido por su id y se agregue en el listado de stocks
        product.setId(id); //le establecemos el id que se obtendrá desde la url
        List<Stock> stocks = stockService.getStockByProduct(product);
        model.addAttribute("stocks", stocks); //enviando la informacion que se mostrará en la vista
        model.addAttribute("idproduct", id); //le pasamos el id de producto que va a ser recorrido en la vista
        return "admin/stock/show"; //Indicando que retorne el resultado en la vista show de stock
    }

    @GetMapping("create-unit-product/{id}")
    public String create(@PathVariable Integer id, Model model){
        model.addAttribute("idproduct", id);
        return "admin/stock/create";
    }

    @PostMapping("/save-unit-product")
    public String save(Stock stock, @RequestParam("idproduct") Integer idproduct){
        //Guardando el stock del producto por medio del id
        stock.setDateCreated(LocalDateTime.now());
        stock.setDescription("inventario");
        Product product = new Product();
        product.setId(idproduct);
        stock.setProduct(product);
        //Calculando el balance del stock
        stockService.saveStock(validateStock.calculateBalance(stock));
        return "redirect:/admin/products/show";
    }
}
