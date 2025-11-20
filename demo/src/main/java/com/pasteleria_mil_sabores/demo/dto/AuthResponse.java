package com.pasteleria_mil_sabores.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private String rol;
    private Long id;   // ← ESTE CAMPO ES OBLIGATORIO
}
