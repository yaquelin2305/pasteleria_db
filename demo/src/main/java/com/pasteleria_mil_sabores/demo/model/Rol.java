package com.pasteleria_mil_sabores.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Ejemplo de nombres: ROLE_USER, ROLE_ADMIN
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
}
