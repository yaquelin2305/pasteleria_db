package com.pasteleria_mil_sabores.demo.controller;

import com.pasteleria_mil_sabores.demo.model.Carrito;
import com.pasteleria_mil_sabores.demo.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:5173")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Carrito> obtenerOCrearCarrito(@PathVariable Integer idUsuario) {
        Carrito carrito = carritoService.obtenerOCrearCarrito(idUsuario);
        return ResponseEntity.ok(carrito);
    }
}
