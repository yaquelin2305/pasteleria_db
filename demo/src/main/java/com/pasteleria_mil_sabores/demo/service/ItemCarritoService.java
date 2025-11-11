package com.pasteleria_mil_sabores.demo.service;

import com.pasteleria_mil_sabores.demo.model.ItemCarro;
import com.pasteleria_mil_sabores.demo.repository.ItemCarritoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemCarritoService {

    private final ItemCarritoRepository itemRepo;

    public ItemCarritoService(ItemCarritoRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    public List<ItemCarro> listar() { return itemRepo.findAll(); }

    public Optional<ItemCarro> obtenerPorId(Integer id) { return itemRepo.findById(id); }
}
