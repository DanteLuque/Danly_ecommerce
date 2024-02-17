package com.Danly.ecommerce.infrastructure.controller;

import com.Danly.ecommerce.application.service.CartService;
import com.Danly.ecommerce.application.service.StockService;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.Stock;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/user/cart")
@Slf4j
public class CartController {
    private final CartService cartService;
    private final StockService stockService;

    public CartController(CartService cartService, StockService stockService) {
        this.cartService = cartService;
        this.stockService = stockService;

    }

    @PostMapping("/add-product")
    public String addProductCart(@RequestParam Integer quantity, @RequestParam Integer idProduct, @RequestParam String nameProduct, @RequestParam BigDecimal price){
        Product product = new Product();
        product.setId(idProduct);

        Stock stock = stockService.getTotalStockByProduct(product);
        int balance = stock.getBalance();

        if(quantity > 1 && quantity <= balance) {
            cartService.addItemCart(quantity, idProduct, nameProduct, price); //añadiendo un nuevo producto al carrito de compras junto a todas las caracteristicas necesarias
        }
        showCart();
        return "redirect:/home";
    }

    private void showCart() {
        cartService.getItemCarts().forEach(
                itemCart -> log.info("Items del carrito: {}", itemCart)
        );
    }

    @GetMapping("/get-cart")
    public String getCart(Model model, HttpSession httpSession){
        showCart();
        model.addAttribute("cart", cartService.getItemCarts());
        model.addAttribute("total", cartService.getTotalCart());
        model.addAttribute("id", httpSession.getAttribute("iduser").toString()); //enviando la variable de sesion
        return "user/cart/cart";
    }

    @GetMapping("/delete-item-cart/{id}")
    public String deleteItemCart(@PathVariable Integer id){
        cartService.removeItemCart(id);
        return "redirect:/user/cart/get-cart";
    }
}
