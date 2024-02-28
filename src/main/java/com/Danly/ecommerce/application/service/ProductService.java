package com.Danly.ecommerce.application.service;


import com.Danly.ecommerce.application.repository.ProductRepository;
import com.Danly.ecommerce.domain.Product;
import com.Danly.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProductService {

    //constructor
    private final ProductRepository productRepository;
    private final UploadFile uploadFile;

    public ProductService(ProductRepository productRepository, UploadFile uploadFile) {
        this.productRepository = productRepository;
        this.uploadFile = uploadFile;
    }

    //metodos que inicializan o ejecutan los servicios establecidos en ProductRepository
    public Iterable<Product> getProducts(){ //Obtener toda la lista de productos
        return  productRepository.getProducts();
    }


    public Iterable<Product> getProductsByUser(User user){ //Obteniendo producto por usuario
        return productRepository.getProductsByUser(user);
    }

    public Product getProductById(Integer id){ //obteniendo producto por id
        return  productRepository.getProductById(id);
    }


    public Product saveProduct(Product product, MultipartFile multipartFile, HttpSession httpSession) throws IOException { //Guardado o actualizado de productos
        if(product.getId()==null){ //Si es id es igual a null, es decir, si es nuevo, realizará un guardado
            User user = new User();
            //user.setId(1); //asignando al usuario creando en la database
            user.setId(Integer.parseInt(httpSession.getAttribute("iduser").toString())); //convertimos el objeto httpsesion en una cadena para luego convertir el valor en un entero y establecer el id del usuario
            product.setDateCreated(LocalDateTime.now()); //asignando la fecha actual para que se estableca automaticamente al crear un producto
            product.setDateUpdated(LocalDateTime.now()); //asignando la fecha de la actualizacion
            product.setUser(user); //le pasamos el usuario para que sea insertado en la informacion del producto creado
            product.setImage(uploadFile.upload(multipartFile)); //estableciendo la imagen, requiere manejo de excepcion a nivel de metodo
            return productRepository.saveProduct(product);
        }else{ //si ya existe, lo actualizamos
            Product productExistente = productRepository.getProductById(product.getId());
            if(multipartFile.isEmpty()){ //si esta variable esta vacia, es decir, si al actualizar el producto, no va a modificar la imagen
                product.setImage(productExistente.getImage()); //Establecemos la imagen que ya tenia establecida
            }else{
                if(!productExistente.getImage().equals("default.jpg")){ //si la imagen del producto existente no es igual a la imagen por defecto
                    uploadFile.delete(productExistente.getImage()); //eliminaremos la imagen anterior ya que no se usará cuando el usuario la cambie por otra, evitando que se llene el directorio "Images" de imagenes no utilizadas
                }
                product.setImage(uploadFile.upload(multipartFile));//modificamos la imagen, en caso de que asi se requiera
            }
            product.setCode(productExistente.getCode()); //reasignamos el codigo
            product.setUser(productExistente.getUser()); //garantizando que el usuario no se borrará al actualizar su producto ya registrado
            product.setDateCreated(productExistente.getDateCreated()); //lo mismo con la fecha de creacion
            product.setDateUpdated(LocalDateTime.now()); //la fecha de actualizacion si la cambiamos por la fecha actual
            return productRepository.saveProduct(product);
        }
    }
    public void deleteProductById(Integer id){ //eliminacion de producto por id
        productRepository.deleteProductById(id);
    }
}
