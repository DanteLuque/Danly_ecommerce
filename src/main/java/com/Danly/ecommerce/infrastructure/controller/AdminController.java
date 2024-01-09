package com.Danly.ecommerce.infrastructure.controller;

import com.Danly.ecommerce.application.service.ProductService;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //indicar que es un controlador
@RequestMapping("/admin") //indicando el nombre de la url
public class AdminController {

    //constructor
    private final ProductService productService;
    public AdminController(ProductService productService) { //inyectando el objeto productService en el controlador de AdminController
        this.productService = productService;
    }

    @GetMapping //indicando que es una peticion de tipo GET
    public String home(Model model){
        User user = new User();
        user.setId(1); //inicializando con el primer usuario de la db
        Iterable<Product> products = productService.getProductsByUser(user); //Objeto iterable de tipo "Product" cuyo nombre será "products", obtendrá solo los productos de un usuario determinado
        model.addAttribute("productos", products); //Model posee un metodo "addAttribute" que recibe un String para el nombre de la vista y un value, en este caso, el objeto "products"

        return "admin/home"; //indicando que use el template "home" del directorio "admin", no el home principal
    }
}
