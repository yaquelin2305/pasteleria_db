package com.pasteleria_mil_sabores.demo.controller;

import com.pasteleria_mil_sabores.demo.model.Producto;
import com.pasteleria_mil_sabores.demo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones CRUD para la gestión de productos de la pastelería")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(
            summary = "Listar todos los productos",
            description = "Obtiene una lista con todos los productos registrados en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de productos obtenida exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Producto.class))
                    )
            }
    )
    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    @Operation(
            summary = "Obtener un producto por ID",
            description = "Busca un producto específico por su identificador único.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Producto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear un nuevo producto",
            description = "Agrega un nuevo producto al catálogo con la información proporcionada.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto creado correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Producto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos para crear el producto",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public Producto crear(@RequestBody Producto p) {
        return productoService.guardar(p);
    }

    @Operation(
            summary = "Actualizar un producto existente",
            description = "Modifica los datos de un producto ya registrado en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto actualizado correctamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Producto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró el producto con el ID especificado",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto p) {
        if (productoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        p.setId(id);
        return ResponseEntity.ok(productoService.guardar(p));
    }

    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto del catálogo según su identificador.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Producto eliminado exitosamente",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (productoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
