//clase que se encargará de la subida y eliminacion de archivos
package com.Danly.ecommerce.application.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadFile {
    private final String FOLDER = "images//"; //ruta donde se guardarán las imagenes
    private final String IMG_DEFAULT = "default.jpg"; //ingresando una imagen por defecto en caso de que no se haya subido una imagen

    public String upload(MultipartFile multipartFile) throws IOException {//dato de tipo "MultiparFile", guardará la imagen y la pasamos como argumento
        if(!multipartFile.isEmpty()){ //si la variable no está vacia
            byte [] bytes = multipartFile.getBytes(); //almacenamos los bytes de la imagen en un arreglo, esto requiere un manejo de excepciones en caso de que no exista la imagen
            //Llamando al Path o la ruta total donde se guardará la imagen
            Path path = Paths.get(FOLDER + multipartFile.getOriginalFilename()); //Concatenamos "Nombre de la ruta" + "Nombre de la imagen", obtenemos el nombre original
            Files.write(path, bytes); //Escribimos el archivo, pasandole la ruta y la imagen
            return multipartFile.getOriginalFilename(); //Una vez que el proceso de subir la imagen a la carpeta "images" este completado, retornaremos la ruta de la imagen subida para que sea tomada por el controlador correspondiente
        }
        return IMG_DEFAULT; //en caso de que no exista o no haya una imagen, retornaremos la imagen por defecto
    } 

    //eliminaremos la imagen en caso de que se elimine o se actualice un producto
    public void delete(String nameFile){
        File file = new File(FOLDER + nameFile);
        file.delete();
    }
}
