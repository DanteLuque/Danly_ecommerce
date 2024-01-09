package com.Danly.ecommerce.infrastructure.controller;


//controlador que nos servirá para mapear la ruta /home
import com.Danly.ecommerce.application.service.ProductService;
import com.Danly.ecommerce.application.service.StockService;
import com.Danly.ecommerce.domain.Stock;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/home")
@Slf4j
public class HomeController {
    private final ProductService productService;
    private final StockService stockService;

    public HomeController(ProductService productService, StockService stockService) {
        this.productService = productService;
        this.stockService = stockService;
    }

    @GetMapping
    public String home(Model model, HttpSession httpSession){
        model.addAttribute("productos", productService.getProducts()); //mostrando todos los productos
        try{
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        }catch(Exception e){
            //La primera vez que arranque la aplicacion, no habrá ningun usuario logeado, asi que dicho model retornará un null
        }
        return "home"; //retornando a la vista home
    }


    @GetMapping("/product-detail/{id}")
    public String productDetail(@PathVariable Integer id, Model model, HttpSession httpSession){
        List<Stock> stocks = stockService.getStockByProduct(productService.getProductById(id));
        log.info("Id product: {}", id);
        log.info("stock size: {}", stocks.size());
        Integer lastBalance = stocks.get(stocks.size()-1).getBalance();

        model.addAttribute("producto", productService.getProductById(id)); //mostrando la informacion de un producto en especifico
        model.addAttribute("stock", lastBalance); //mostrando el stock de dicho producto
        try{
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        }catch(Exception e){
            //La primera vez que arranque la aplicacion, no habrá ningun usuario logeado, asi que dicho model retornará un null
        }
        return "user/productdetail";
    }
}

