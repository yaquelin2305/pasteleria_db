package com.pasteleria_mil_sabores.demo.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "usuario")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    // Clave primaria de la tabla (auto incremental)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Nombre del usuario (campo obligatorio)
    @Column(nullable = false, length = 100)
    private String nombre;

    // Apellido del usuario (opcional)
    @Column(length = 100)
    private String apellido;

    // Correo electrónico único (se usa también para login)
    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    // Contraseña encriptada o sin encriptar (campo obligatorio)
    @Column(nullable = false, length = 255)
    private String contrasena;

    // Relación con la tabla "roles"
    // Muchos usuarios pueden tener un mismo rol (por ejemplo, ADMIN o CLIENTE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol") // Este campo se conecta con la clave foránea en la tabla usuarios
    private Rol rol;
}
