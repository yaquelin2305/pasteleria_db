package com.pasteleria_mil_sabores.demo.service;

import com.pasteleria_mil_sabores.demo.dto.AuthRequest;
import com.pasteleria_mil_sabores.demo.dto.AuthResponse;
import com.pasteleria_mil_sabores.demo.dto.RegisterRequest;
import com.pasteleria_mil_sabores.demo.model.Usuario;
import com.pasteleria_mil_sabores.demo.model.Rol;
import com.pasteleria_mil_sabores.demo.repository.UsuarioRepository;
import com.pasteleria_mil_sabores.demo.repository.RolRepository;
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


    // LOGIN

    public AuthResponse login(AuthRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getContrasena()
                )
        );

        Usuario usuario = usuarioRepo.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        //  GENERAR TOKEN con (correo, rol, id)
        String token = jwtUtil.generateToken(
                usuario.getCorreo(),
                usuario.getRol().getNombre(),
                usuario.getId()
        );

        return new AuthResponse(
                token,
                usuario.getRol().getNombre(),
                usuario.getId()
        );
    }




    public AuthResponse register(RegisterRequest request) {

        if (usuarioRepo.findByCorreo(request.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.getNombre());
        nuevo.setApellido(request.getApellido());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setContrasena(passwordEncoder.encode(request.getContrasena()));
        nuevo.setTelefono(request.getTelefono());
        nuevo.setDireccion(request.getDireccion());
        nuevo.setRegion(request.getRegion());
        nuevo.setComuna(request.getComuna());

        Rol rolCliente = rolRepo.findByNombre("ROL_CLIENTE");
        if (rolCliente == null) {
            throw new RuntimeException("Rol ROLE_CLIENTE no existe en la base de datos.");
        }

        nuevo.setRol(rolCliente);

        usuarioRepo.save(nuevo);


        String token = jwtUtil.generateToken(
                nuevo.getCorreo(),
                nuevo.getRol().getNombre(),
                nuevo.getId()
        );

        return new AuthResponse(
                token,
                nuevo.getRol().getNombre(),
                nuevo.getId()
        );
    }
}
