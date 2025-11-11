package com.pasteleria_mil_sabores.demo.controller;

import com.pasteleria_mil_sabores.demo.model.ItemCarro;
import com.pasteleria_mil_sabores.demo.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item-carrito")
@CrossOrigin(origins = "http://localhost:5173")
public class ItemCarritoController {

    private final CarritoService carritoService;

    public ItemCarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @PostMapping
    public ResponseEntity<ItemCarro> agregar(
            @RequestParam Integer idCarrito,
            @RequestParam Integer idProducto,
            @RequestParam(defaultValue = "1") Integer cantidad) {
        ItemCarro item = carritoService.agregarItem(idCarrito, idProducto, cantidad);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{idItem}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idItem) {
        carritoService.eliminarItem(idItem);
        return ResponseEntity.noContent().build();
    }
}
