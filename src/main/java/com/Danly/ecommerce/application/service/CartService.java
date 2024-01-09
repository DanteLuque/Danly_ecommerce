package com.Danly.ecommerce.application.service;

import com.Danly.ecommerce.domain.ItemCart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartService {

    private List<ItemCart> itemCarts;
    private HashMap<Integer, ItemCart> itemCartHashMap; //ALmacen clave-valor para evitar repeticiones (id del producto, producto)

    public CartService() {
        this.itemCartHashMap = new HashMap<>();
        this.itemCarts = new ArrayList<>();
    }

    public void addItemCart(Integer quantity, Integer idProduct, String nameProduct, BigDecimal price){
        ItemCart itemCart = new ItemCart(idProduct, nameProduct, quantity, price); //Pasandole los argumentos en base al orden de los atributos en la clase ItemCart
        itemCartHashMap.put(itemCart.getIdProduct(), itemCart); //Agregando un producto al carrito de compras con el id del producto y el objeto entero que representa al producto
        fillList();
    }

    public BigDecimal getTotalCart(){
        BigDecimal total = BigDecimal.ZERO; //inicializnado con una constante CERO
        for(ItemCart i : itemCarts){
            total = total.add(i.getTotalPriceItem()); //Obteniendo el precio total de toda la compra
        }
        return total;
    }

    public void removeItemCart(Integer idProduct){
        itemCartHashMap.remove(idProduct); //Eliminar un producto por id
        fillList();
    }

    public void removeAllItemsCart(){ //eliminando toda la lista
        itemCartHashMap.clear();
        itemCarts.clear();
    }

    private  void fillList(){
        itemCarts.clear(); //vaceando la lista
        itemCartHashMap.forEach(
                (integer, itemCart)-> itemCarts.add(itemCart) //Pasando de HashMap a List, haciendola más facil de recorrer
        );
    }

    //Para mirar por consola y comprobar que se esten añadiendo los productos al carrito de compras
    public List<ItemCart> getItemCarts(){
        return itemCarts;
    }
}
