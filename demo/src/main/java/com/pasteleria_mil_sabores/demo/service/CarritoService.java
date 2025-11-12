package com.pasteleria_mil_sabores.demo.service;

import com.pasteleria_mil_sabores.demo.model.*;
import com.pasteleria_mil_sabores.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepo;
    private final ItemCarritoRepository itemRepo;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    public CarritoService(CarritoRepository carritoRepo,
                          ItemCarritoRepository itemRepo,
                          ProductoRepository productoRepo,
                          UsuarioRepository usuarioRepo) {
        this.carritoRepo = carritoRepo;
        this.itemRepo = itemRepo;
        this.productoRepo = productoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    //  Obtener o crear carrito por usuario
    @Transactional
    public Carrito obtenerOCrearCarrito(Long idUsuario) {
        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        return carritoRepo.findByUsuario(usuario).orElseGet(() -> {
            Carrito nuevo = new Carrito();
            nuevo.setUsuario(usuario);
            nuevo.setFecha(LocalDateTime.now());
            nuevo.setTotal(BigDecimal.ZERO);
            nuevo.setItems(new ArrayList<>());
            return carritoRepo.save(nuevo);
        });
    }

    //  Obtener carrito por su ID
    @Transactional(readOnly = true)
    public Carrito obtenerPorId(Long idCarrito) {
        return carritoRepo.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + idCarrito));
    }

    //  Agregar producto al carrito
    @Transactional
    public ItemCarro agregarItem(Long idCarrito, Long idProducto, Integer cantidad) {
        Carrito carrito = carritoRepo.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        BigDecimal precio = producto.getPrecio() != null ? producto.getPrecio() : BigDecimal.ZERO;
        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));

        ItemCarro item = new ItemCarro();
        item.setCarrito(carrito);
        item.setProducto(producto);
        item.setCantidad(cantidad);
        item.setSubtotal(subtotal);

        // Si el carrito no tiene lista de items, inicialízala
        if (carrito.getItems() == null) {
            carrito.setItems(new ArrayList<>());
        }

        carrito.getItems().add(item);

        BigDecimal totalActual = carrito.getTotal() != null ? carrito.getTotal() : BigDecimal.ZERO;
        BigDecimal nuevoTotal = totalActual.add(subtotal);
        carrito.setTotal(nuevoTotal);

        carritoRepo.save(carrito);
        return itemRepo.save(item);
    }

    // Eliminar item del carrito
    @Transactional
    public void eliminarItem(Long idItem) {
        ItemCarro item = itemRepo.findById(idItem)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        Carrito carrito = item.getCarrito();

        BigDecimal subtotal = item.getSubtotal() != null ? item.getSubtotal() : BigDecimal.ZERO;
        BigDecimal totalActual = carrito.getTotal() != null ? carrito.getTotal() : BigDecimal.ZERO;
        BigDecimal nuevoTotal = totalActual.subtract(subtotal);

        carrito.setTotal(nuevoTotal);
        carrito.getItems().remove(item);
        itemRepo.delete(item);
        carritoRepo.save(carrito);
    }
}
