package com.pasteleria_mil_sabores.demo.controller;

import com.pasteleria_mil_sabores.demo.model.ItemCarro;
import com.pasteleria_mil_sabores.demo.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
@RequiredArgsConstructor
@Tag(name = "Carrito", description = "Operaciones públicas del carrito de compras")
@CrossOrigin("*")
public class CarritoController {

    private final CarritoService carritoService;


    @PostMapping("/agregar/{productoId}")
    @Operation(summary = "Agregar un producto al carrito (PÚBLICO)")
    public ResponseEntity<ItemCarro> agregarProducto(
            @PathVariable Long productoId) {

        ItemCarro item = carritoService.agregarProducto(productoId);
        return ResponseEntity.ok(item);
    }


    @GetMapping
    @Operation(summary = "Obtener el carrito completo (PÚBLICO)")
    public ResponseEntity<List<ItemCarro>> obtenerCarrito() {
        return ResponseEntity.ok(carritoService.obtenerCarrito());
    }


    @DeleteMapping("/eliminar/{itemId}")
    @Operation(summary = "Eliminar un item del carrito (PÚBLICO)")
    public ResponseEntity<String> eliminarItem(@PathVariable Long itemId) {
        carritoService.eliminarItem(itemId);
        return ResponseEntity.ok("Ítem eliminado del carrito");
    }
}
