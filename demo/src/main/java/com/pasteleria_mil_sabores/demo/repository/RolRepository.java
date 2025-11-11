package com.pasteleria_mil_sabores.demo.repository;

import com.pasteleria_mil_sabores.demo.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);
}
