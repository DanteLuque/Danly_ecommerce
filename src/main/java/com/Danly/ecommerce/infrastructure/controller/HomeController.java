package com.Danly.ecommerce.infrastructure.controller;


//controlador que nos servirá para mapear la ruta /home
import com.Danly.ecommerce.application.service.ProductService;
import com.Danly.ecommerce.application.service.StockService;

import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.Stock;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String home(@RequestParam(value = "searchTerm", required = false)  String searchTerm, Model model, HttpSession httpSession){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        Iterable<Product> productos = (searchTerm != null && !searchTerm.isEmpty()) ?  productService.findByNameContainingIgnoreCase(searchTerm) : productService.getProducts() ;
        List<Integer> stockList = new ArrayList<>();

        for (Product i : productos) {
            List<Stock> stocks = stockService.getStockByProduct(i);
            if (!stocks.isEmpty()) {
                Integer lastBalance = stocks.get(stocks.size() - 1).getBalance(); // Obtener el último stock de cada producto
                stockList.add(lastBalance);
            }else {
                stockList.add(0); // Si no hay stock para este producto, agregar un valor predeterminado
            }
        }
        model.addAttribute("productos",productos); //mostrando todos los productos
        model.addAttribute("stocks", stockList); //mostrando toda la lista de stock
        try{
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
            model.addAttribute("nombreUsuario", username);// Agregar el nombre de usuario al modelo para mostrar en la vista
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



