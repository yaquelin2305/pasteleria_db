package com.pasteleria_mil_sabores.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pasteleriaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🍰 API - Pastelería Mil Sabores")
                        .version("1.0.0")
                        .description("""
                                Documentación interactiva de la API REST del proyecto *Pastelería Mil Sabores*.

                                Aquí puedes visualizar y probar todos los endpoints disponibles:
                                - Autenticación de usuarios
                                - Gestión de productos
                                - Carrito de compras
                                - Items del carrito

                                Proyecto desarrollado para la Evaluación Parcial 3 (Backend + Frontend React + MySQL)
                                """)
                        .contact(new Contact()
                                .name("Yaquelin Rugel y Yeider Catari")
                                .email("soporte@mil-sabores.com"))
                        .license(new License()
                                .name("Uso académico")
                                .url("http://localhost:8080/swagger-ui/index.html")));
    }
}
