package com.pasteleria_mil_sabores.demo.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String correo;
    private String contrasena;
}
