// Paso 2: Archivo: AppConfig.java
// Define la configuración del bean RestTemplate para la aplicación.

package com.nexify.openlibraryapi.config;  // Paso 2.1: Define el paquete en el que se encuentra esta clase

// Paso 2.2: Importaciones necesarias para esta configuración
import org.springframework.context.annotation.Bean;  // Anotación para definir un bean en Spring
import org.springframework.context.annotation.Configuration;  // Indica que esta clase tiene configuraciones de Spring
import org.springframework.web.client.RestTemplate;  // Clase que facilita las operaciones de HTTP

@Configuration  // Paso 2.3: Indica que esta clase contiene configuraciones de Spring y puede definir beans
public class AppConfig {
    /**
     * Un bean en Spring es un objeto que forma parte del contenedor de Spring,
     * también conocido como el IoC (Inversion of Control) container.
     * Estos beans son gestionados por el contenedor de Spring, lo que significa que
     * Spring se encarga de instanciar, configurar y ensamblar los objetos.
     */

    // Paso 2.4: Define un bean para RestTemplate
    @Bean  // Define que el metodo restTemplate() produce un bean gestionado por Spring
    public RestTemplate restTemplate() {
        return new RestTemplate();  // Crea y devuelve una nueva instancia de RestTemplate
    }
}
