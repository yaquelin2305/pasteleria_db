package com.pasteleria_mil_sabores.demo.service;

import com.pasteleria_mil_sabores.demo.model.*;
import com.pasteleria_mil_sabores.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepo;
    private final ItemCarritoRepository itemRepo;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    public CarritoService(CarritoRepository carritoRepo, ItemCarritoRepository itemRepo,
                          ProductoRepository productoRepo, UsuarioRepository usuarioRepo) {
        this.carritoRepo = carritoRepo;
        this.itemRepo = itemRepo;
        this.productoRepo = productoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Transactional
    public Carrito obtenerOCrearCarrito(Integer idUsuario) {
        Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow();
        return carritoRepo.findByUsuario(usuario).orElseGet(() -> {
            Carrito nuevo = new Carrito();
            nuevo.setUsuario(usuario);
            return carritoRepo.save(nuevo);
        });
    }

    @Transactional
    public ItemCarro agregarItem(Integer idCarrito, Integer idProducto, Integer cantidad) {
        Carrito carrito = carritoRepo.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        //  Evitar null en precio
        BigDecimal precio = producto.getPrecio() != null ? producto.getPrecio() : BigDecimal.ZERO;
        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));

        // Crear el nuevo ítem
        ItemCarro item = new ItemCarro();
        item.setCarrito(carrito);
        item.setProducto(producto);
        item.setCantidad(cantidad);
        item.setSubtotal(subtotal);

        carrito.getItems().add(item);

        //  Usar BigDecimal
        BigDecimal totalActual = carrito.getTotal() != null ? carrito.getTotal() : BigDecimal.ZERO;
        BigDecimal nuevoTotal = totalActual.add(subtotal);
        carrito.setTotal(nuevoTotal);

        carritoRepo.save(carrito);
        return item;
    }


    @Transactional
    public void eliminarItem(Integer idItem) {
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
