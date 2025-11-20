package com.pasteleria_mil_sabores.demo.repository;

import com.pasteleria_mil_sabores.demo.model.Carrito;
import com.pasteleria_mil_sabores.demo.model.ItemCarro;
import com.pasteleria_mil_sabores.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCarritoRepository extends JpaRepository<ItemCarro, Long> {

    List<ItemCarro> findByCarrito(Carrito carrito);

    Optional<ItemCarro> findFirstByCarritoAndProducto(Carrito carrito, Producto producto);
}
