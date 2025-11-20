package com.pasteleria_mil_sabores.demo.repository;

import com.pasteleria_mil_sabores.demo.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
