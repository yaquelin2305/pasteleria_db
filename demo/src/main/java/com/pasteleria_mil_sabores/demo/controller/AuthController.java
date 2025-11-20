package com.pasteleria_mil_sabores.demo.controller;

import com.pasteleria_mil_sabores.demo.dto.*;
import com.pasteleria_mil_sabores.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para registro e inicio de sesión con JWT")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Iniciar sesión de usuario",
            description = "Recibe un usuario y contraseña, valida las credenciales y genera un token JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Inicio de sesión exitoso, devuelve el token JWT",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciales incorrectas",
                            content = @Content
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea un nuevo usuario en la base de datos y devuelve un token JWT de autenticación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de registro inválidos o usuario existente",
                            content = @Content
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
