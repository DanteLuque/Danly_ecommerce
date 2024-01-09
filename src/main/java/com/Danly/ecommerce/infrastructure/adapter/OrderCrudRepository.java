//Aqui implementamos la interfaz de CrudRepository que nos brinda la API de JPA
//Adaptamos este metodo para que podamos usar todos lo de un CRUD
//En caso de requerir un metodo diferente, lo especificamos
package com.Danly.ecommerce.infrastructure.adapter;

import com.Danly.ecommerce.infrastructure.entity.OrderEntity;
import com.Danly.ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderCrudRepository extends CrudRepository<OrderEntity , Integer>{ //Le pasamos la entidad "OrderEntity" ya que ahi se aplicará el CRUD
    public Iterable<OrderEntity> findByUser(UserEntity userEntity); //Ahora podemos obtener una lista filtrada por un usuario, la entidad "userEntity" está ligada a OrderEntity
}
