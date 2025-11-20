package com.pasteleria_mil_sabores.demo.service;

import com.pasteleria_mil_sabores.demo.model.Carrito;
import com.pasteleria_mil_sabores.demo.model.ItemCarro;
import com.pasteleria_mil_sabores.demo.model.Producto;
import com.pasteleria_mil_sabores.demo.repository.CarritoRepository;
import com.pasteleria_mil_sabores.demo.repository.ItemCarritoRepository;
import com.pasteleria_mil_sabores.demo.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepo;
    private final ItemCarritoRepository itemRepo;
    private final ProductoRepository productoRepo;


    private Carrito obtenerCarritoUnico() {


        List<Carrito> carritos = carritoRepo.findAll();

        if (!carritos.isEmpty()) {
            return carritos.get(0); // usar el primero
        }


        Carrito nuevo = new Carrito();
        return carritoRepo.save(nuevo);  // ID se genera solo
    }


    public ItemCarro agregarProducto(Long productoId) {

        Carrito carrito = obtenerCarritoUnico();
        Producto producto = productoRepo.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Ver si el producto ya está en el carrito
        return itemRepo.findFirstByCarritoAndProducto(carrito, producto)

                .map(itemExistente -> {
                    itemExistente.setCantidad(itemExistente.getCantidad() + 1);
                    return itemRepo.save(itemExistente);
                })
                .orElseGet(() -> {
                    ItemCarro nuevo = new ItemCarro();
                    nuevo.setCarrito(carrito);
                    nuevo.setProducto(producto);
                    nuevo.setCantidad(1);
                    return itemRepo.save(nuevo);
                });
    }


    public List<ItemCarro> obtenerCarrito() {
        Carrito carrito = obtenerCarritoUnico();
        return itemRepo.findByCarrito(carrito);
    }


    public void eliminarItem(Long itemId) {
        if (!itemRepo.existsById(itemId)) {
            throw new RuntimeException("Ítem no encontrado");
        }
        itemRepo.deleteById(itemId);
    }
}
