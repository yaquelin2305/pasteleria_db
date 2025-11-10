package com.pasteleria_mil_sabores.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "¡Bienvenida a la Pastelería Mil Sabores 🍰!";
    }
}
