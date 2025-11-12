package com.pasteleria_mil_sabores.demo.service;

import com.pasteleria_mil_sabores.demo.model.Producto;
import com.pasteleria_mil_sabores.demo.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepo;

    public ProductoService(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    public List<Producto> listar() {
        return productoRepo.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepo.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepo.save(producto);
    }

    public void eliminar(Long id) {
        productoRepo.deleteById(id);
    }
}
