//VALIDANDO LOS DATOS PARA EL REGISTRO DE UN USUARIO
package com.Danly.ecommerce.infrastructure.dto;

import com.Danly.ecommerce.domain.User;
import com.Danly.ecommerce.domain.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private String username; //Esto no lo validamos ya que lo procesamos internamente

    @NotBlank(message = "Nombre es requerido") //Indicamos que este campo debe estar lleno, caso contrario, le mandamos este mensaje
    private String firstName;
    @NotBlank(message = "Apellido es requerido")
    private String lastName;
    @Email(message = "Debe ingresar un email valido") //anotacion para asegurar de que el email ingresado sea valido
    private String email;
    @NotBlank(message = "Direccion es requerido") //Indicamos que este campo debe estar lleno, caso contrario, le mandamos este mensaje
    private String address;
    @NotBlank(message = "Numero de telefono es requerido") //Indicamos que este campo debe estar lleno, caso contrario, le mandamos este mensaje
    private String cellphone;
    @NotBlank(message = "contraseña es requerida") //Indicamos que este campo debe estar lleno, caso contrario, le mandamos este mensaje
    private String password;

    public User userDtoToUser(){ //convierte userDto a usuario
        return new User(null, this.getEmail(), this.getFirstName(), this.getLastName(), this.getEmail(), this.getAddress(), this.getCellphone(), this.getPassword(), UserType.USER, LocalDateTime.now()); //Creamos un nuevo usuario para que la UserDto se convierta a USER
    }
}
