package com.Danly.ecommerce.infrastructure.controller;

import com.Danly.ecommerce.application.service.ProductService;
import com.Danly.ecommerce.application.service.StockService;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.Stock;
import com.Danly.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller //indicar que es un controlador
@RequestMapping("/admin") //indicando el nombre de la url
public class AdminController {

    //constructor
    private final ProductService productService;
    private final StockService stockService;

    public AdminController(ProductService productService, StockService stockService) { //inyectando el objeto productService en el controlador de AdminController
        this.productService = productService;
        this.stockService = stockService;
    }

    @GetMapping //indicando que es una peticion de tipo GET
    public String home(Model model, HttpSession httpSession){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = new User();
        user.setId(Integer.parseInt(httpSession.getAttribute("iduser").toString()));

        Iterable<Product> products = productService.getProductsByUser(user); //Objeto iterable de tipo "Product" cuyo nombre será "products", obtendrá solo los productos de un usuario determinado

        List<Integer> stockList = new ArrayList<>();
        for (Product i : products) {
            List<Stock> stocks = stockService.getStockByProduct(i);
            if (!stocks.isEmpty()) {
                // Obtener el último stock de cada producto
                Integer lastBalance = stocks.get(stocks.size() - 1).getBalance();
                stockList.add(lastBalance);

            } else {
                // Si no hay stock para este producto, agregar un valor predeterminado
                stockList.add(0);
            }
        }

        model.addAttribute("productos", products); //Model posee un metodo "addAttribute" que recibe un String para el nombre de la vista y un value, en este caso, el objeto "products"
        model.addAttribute("stocks", stockList); //mostrando toda la lista de stock
        try{
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
            model.addAttribute("nombreUsuario", username);
        }catch(Exception e){
            //La primera vez que arranque la aplicacion, no habrá ningun usuario logeado, asi que dicho model retornará un null
        }


        return "admin/home"; //indicando que use el template "home" del directorio "admin", no el home principal
    }
}
