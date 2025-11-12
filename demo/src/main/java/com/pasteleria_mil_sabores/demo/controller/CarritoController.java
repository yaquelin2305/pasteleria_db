package com.pasteleria_mil_sabores.demo.controller;

import com.pasteleria_mil_sabores.demo.model.Carrito;
import com.pasteleria_mil_sabores.demo.model.ItemCarro;
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

    // ✅ Obtener o crear carrito según usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Carrito> obtenerOCrearCarrito(@PathVariable Long idUsuario) {
        Carrito carrito = carritoService.obtenerOCrearCarrito(idUsuario);
        return ResponseEntity.ok(carrito);
    }

    // ✅ Obtener carrito por ID
    @GetMapping("/{idCarrito}")
    public ResponseEntity<?> obtenerCarritoPorId(@PathVariable Long idCarrito) {
        try {
            Carrito carrito = carritoService.obtenerPorId(idCarrito);
            return ResponseEntity.ok(carrito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ✅ Agregar producto al carrito
    @PostMapping("/{idCarrito}/agregar")
    public ResponseEntity<?> agregarItem(
            @PathVariable Long idCarrito,
            @RequestParam Long idProducto,
            @RequestParam Integer cantidad) {
        try {
            ItemCarro item = carritoService.agregarItem(idCarrito, idProducto, cantidad);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Eliminar producto del carrito
    @DeleteMapping("/item/{idItem}")
    public ResponseEntity<?> eliminarItem(@PathVariable Long idItem) {
        try {
            carritoService.eliminarItem(idItem);
            return ResponseEntity.ok("Item eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
