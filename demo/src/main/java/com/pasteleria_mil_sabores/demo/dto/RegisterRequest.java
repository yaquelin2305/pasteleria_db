package com.pasteleria_mil_sabores.demo.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
}
