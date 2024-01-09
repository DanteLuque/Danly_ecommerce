package com.Danly.ecommerce.infrastructure.controller;


import com.Danly.ecommerce.application.service.ProductService;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/products") //url a nivel de controlador
@Slf4j //anotacion que nos permite poner en cualquier parte de nuestra clase un mensaje por medio de log
public class ProductController {

    //constructor
    private final ProductService productService; //creando una variable de tipo ProductService que sea final para que se pueda inyectar como constructor
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    //Al tener varios metodos GetMapping, debemos ponerle una terminacion

    @GetMapping("/create") //url a nivel de metodo(terminacion)
    public String create(){
        return "admin/products/create";
    }

    @PostMapping("/save-product") //peticion de tipo post, un envio de informacion (las terminaciones de tipos post no aparecen en la url)
    public String saveProduct(Product product, @RequestParam("img") MultipartFile multipartFile, HttpSession httpSession) throws IOException { //la variable multipartFile será reconocida en la vista como "img"
        //con la anotacion @SLf4j nos permite usar log.info() envés de System.out.println() - sout
        log.info("Nombre de producto: {}", product); //imprimiendo directamente el objeto a traves de la anotacion "toString" de este objeto
        productService.saveProduct(product, multipartFile, httpSession); //guardando el producto
        //return "admin/products/create";
        return "redirect:/admin"; //funcion "redirect" de spring boot que nos permite accederdirectamente al controlador "admin", usando en este caso, su unico metodo el cual es mostrar el home de admin
    }

    @GetMapping("/show")
    public String showProduct(Model model, HttpSession httpSession){ //El Objeto Model es proporcionado por Springframework
        log.info("id user desde la variable de sesion {}",httpSession.getAttribute("iduser").toString());
        User user = new User();
        //user.setId(1); //inicializando con el primer usuario de la db
        user.setId(Integer.parseInt(httpSession.getAttribute("iduser").toString()));
        Iterable<Product> products = productService.getProductsByUser(user); //Objeto iterable de tipo "Product" cuyo nombre será "products", obtendrá solo los productos de un usuario determinado
        model.addAttribute("productos", products); //Model posee un metodo "addAttribute" que recibe un String para el nombre de la vista y un value, en este caso, el objeto "products"
        return "admin/products/show"; //mostrando la mista de la pagina "productos" donde se veran los productos registrados
    }

    @GetMapping("/edit/{id}") //concatenando el id correspondiente para que sea ejecutada dentro de la funcion "editProduct"
    public String editProduct(@PathVariable Integer id, Model model){ //Pasando como parametro la variable
        Product product = productService.getProductById(id); //obteniendo el producto mediante el id
        log.info("Producto obtenido: {}", product); //pasando la informacion del producto al terminal
        model.addAttribute("productos",product ); //enviando la informacion del modelo hacia la vista "edit" a traves del controlador ProductController
        return "admin/products/edit";
    }

    @GetMapping("/delete/{id}")
    //PathVariable permite mapear la variable enviada a la url y convertirla a un tipo de dato que java pueda procesar
    public String deleteProduct(@PathVariable Integer id){
        productService.deleteProductById(id); //eliminando el registro del producto
        return "redirect:/admin/products/show"; //mostrando directamente la vista
    }
}
