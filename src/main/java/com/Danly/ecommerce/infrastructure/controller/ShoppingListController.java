package com.Danly.ecommerce.infrastructure.controller;

import com.Danly.ecommerce.application.service.OrderProductService;
import com.Danly.ecommerce.application.service.OrderService;
import com.Danly.ecommerce.application.service.UserService;
import com.Danly.ecommerce.domain.Order;
import com.Danly.ecommerce.domain.OrderProduct;
import com.Danly.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/cart/shopping")
public class ShoppingListController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderProductService orderProductService;

    public ShoppingListController(OrderService orderService, UserService userService, OrderProductService orderProductService) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderProductService = orderProductService;
    }

    @GetMapping
    public String showShoppingList(Model model, HttpSession httpSession){
        List<Order> newListOrder = new ArrayList<>();
        User user = userService.findById(Integer.parseInt(httpSession.getAttribute("iduser").toString()));

        Iterable<Order> orders = orderService.getOrdersByUser(user); //Obteniendo todas las ordenes que registró un usuario
        for(Order i: orders){
            newListOrder.add(getOrdersProducts(i)); //Iterando en cada orden que registre el usuario y lo añadimos a la lista
        }

        model.addAttribute("id", Integer.parseInt(httpSession.getAttribute("iduser").toString()));
        model.addAttribute("ordenes", newListOrder); //Enviando la lista de ordenes llenas

        return "/user/shoppinglist";
    }

    private Order getOrdersProducts(Order order){
        Iterable<OrderProduct> orderProducts = orderProductService.getOrdersProductsByOrder(order); //Lista de productos ordernados
        order.addOrdersProduct((List<OrderProduct>) orderProducts); //Convirtiendo el iterable a una lista y agregamos la orden
        return order; //Retornamos las ordenes
    }

    @GetMapping("/detailShop/{id}")
    public String getDetailShop(@PathVariable Integer id, Model model, HttpSession httpSession){
        log.info("Id de la orden: {}",id);
        User user = userService.findById(Integer.parseInt(httpSession.getAttribute("iduser").toString()));
        Order order = orderService.getOrderByIdAndUser(id, user);

        List<OrderProduct> orderProducts = orderProductService.getOrdersProductsByOrder(order);

        model.addAttribute("detalles",orderProducts);
        model.addAttribute("id", Integer.parseInt(httpSession.getAttribute("iduser").toString()));

        return "user/detailShop";
    }

}
