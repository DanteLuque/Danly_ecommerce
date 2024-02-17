package com.Danly.ecommerce.infrastructure.controller;


import com.Danly.ecommerce.application.service.*;
import com.Danly.ecommerce.domain.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final StockService stockService;
    private final ValidateStock validateStock;
    private final Integer UNIT_IN = 0;

    public OrderController(CartService cartService, UserService userService, ProductService productService, OrderService orderService, OrderProductService orderProductService, StockService stockService, ValidateStock validateStock) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.stockService = stockService;
        this.validateStock = validateStock;
    }


    @GetMapping("/sumary-order")
    public String showSumaryOrder(Model model,   HttpSession httpSession){
        log.info("id user desde la variable de sesion {}",httpSession.getAttribute("iduser").toString());
        User user = userService.findById(Integer.parseInt(httpSession.getAttribute("iduser").toString()));
        //User user = userService.findById(1);
        model.addAttribute("cart", cartService.getItemCarts());
        model.addAttribute("total", cartService.getTotalCart());
        model.addAttribute("user", user);
        model.addAttribute("id", httpSession.getAttribute("iduser").toString()); //enviando la variable de sesion
        return "user/sumaryorder";
    }

    @GetMapping("/create-order")
    public String CreateOrder(HttpSession httpSession, RedirectAttributes attributes){ //Aqui no usamos model por que no estamos haciendo "return" a la vista, sinó que estamos redireccionando
        log.info("Orden creada...");
        //creando usuario temporal para probar esta funcionalidad
        //User user = userService.findById(1);
        log.info("id user desde la variable de sesion {}",httpSession.getAttribute("iduser").toString());
        User user = userService.findById(Integer.parseInt(httpSession.getAttribute("iduser").toString()));


        if(cartService.getTotalCart().compareTo(BigDecimal.ZERO) > 0){ //Solo se generará una orden de compra si el precio total de la lista del carrito es mayor a 0
            //order
            Order order = new Order();
            order.setDateCreated(LocalDateTime.now());
            order.setUser(user);

            order = orderService.createOrder(order); //Guardando la orden

            //Order Product
            List<OrderProduct> orderProducts = new ArrayList<>();

            //ItemCart a OrderProduct
            for (ItemCart itemCart : cartService.getItemCarts()) { //Recorriendo todos los productos dentro del carrito de compras
                orderProducts.add(new OrderProduct(productService.getProductById(itemCart.getIdProduct()), itemCart.getQuantity(), order)); //añadiendo un nuevo item del carrito a la Lista de ordenes
            }

            //Guardando la lista de ordenes
            orderProducts.forEach(
                    op -> {
                        orderProductService.create(op); //Guardando todos los elementos recorridos
                        Stock stock = new Stock();
                        stock.setDateCreated(LocalDateTime.now()); //Fecha actual de la transaccion
                        stock.setProduct(op.getProduct()); //estableciendo el producto
                        stock.setDescription("venta");
                        stock.setUnitIn(UNIT_IN); //Reseteamos el parametro de entrada, se recomienda representar valores estaticos como constantes
                        stock.setUnitOut(op.getQuantity()); //restando la cantidad que fue comprada
                        stockService.saveStock(validateStock.calculateBalance(stock)); //Calculando cuanto queda despues de la venta
                    }
            );
            cartService.removeAllItemsCart(); //limpiando el carrito
            attributes.addFlashAttribute("id", httpSession.getAttribute("iduser").toString()); //enviando la variable de sesion
            return "redirect:/home";
        } else {
            log.info("El resumen de la orden posee un precio menor o igual a 0");
            return "redirect:/home";
        }
    }
}
