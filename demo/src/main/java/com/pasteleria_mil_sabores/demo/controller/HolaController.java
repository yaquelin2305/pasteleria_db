package com.pasteleria_mil_sabores.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/privado")
public class HolaController {

    @GetMapping("/hola/{nombre}")
    public ResponseEntity<String> hola(@PathVariable String nombre) {
        return ResponseEntity.ok("Hola mundo " + nombre);
    }
}
