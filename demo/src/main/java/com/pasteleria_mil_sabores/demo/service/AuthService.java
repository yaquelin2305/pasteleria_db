package com.pasteleria_mil_sabores.demo.service;

import com.pasteleria_mil_sabores.demo.dto.*;
import com.pasteleria_mil_sabores.demo.model.*;
import com.pasteleria_mil_sabores.demo.repository.*;
import com.pasteleria_mil_sabores.demo.util.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authManager,
                       UsuarioRepository usuarioRepo,
                       RolRepository rolRepo,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 🔐 LOGIN: valida usuario y genera token JWT
    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getContrasena()
                )
        );

        String token = jwtUtil.generateToken(request.getCorreo());
        return new AuthResponse(token);
    }

    // 🧾 REGISTRO: crea usuario y devuelve token JWT
    public AuthResponse register(RegisterRequest request) {
        // Evitar duplicados
        if (usuarioRepo.findByCorreo(request.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Crear nuevo usuario con todos los campos
        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setApellido(request.getApellido());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
        nuevo.setTelefono(request.getTelefono());
        nuevo.setDireccion(request.getDireccion());
        nuevo.setRegion(request.getRegion());
        nuevo.setComuna(request.getComuna());

        // Asignar rol CLIENTE por defecto
        Rol rolCliente = rolRepo.findByNombre("CLIENTE");
        if (rolCliente == null) {
            throw new RuntimeException("Rol CLIENTE no existe. Debes crearlo en la base de datos.");
        }
        nuevo.setRol(rolCliente);

        usuarioRepo.save(nuevo);

        // Generar token JWT basado en el correo
        String token = jwtUtil.generateToken(nuevo.getCorreo());

        return new AuthResponse(token);
    }
}
