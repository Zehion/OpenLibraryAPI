// Paso 4: Archivo: HttpHelper.java
// Este archivo contiene la clase HttpHelper que facilita las solicitudes HTTP a la API.

package com.nexify.openlibraryapi.helper;  // Paso 4.1: Define el paquete al que pertenece esta clase

// Paso 4.2: Importaciones necesarias para esta clase
import com.nexify.openlibraryapi.config.ApiConfig;  // Importa la clase ApiConfig
import com.nexify.openlibraryapi.nums.ApiType;  // Importa la enumeración ApiType
import lombok.Getter;  // Importa la anotación @Getter de Lombok para generar automáticamente getters
import org.springframework.stereotype.Component;  // Indica que esta clase es un componente Spring
import org.springframework.web.client.RestTemplate;  // Importa la clase RestTemplate para realizar operaciones HTTP

@Component  // Paso 4.3: Indica que esta clase es un componente gestionado por Spring
public class HttpHelper {

    private final ApiConfig apiConfig;  // Paso 4.4: Declaración de una variable final de tipo ApiConfig
    private final RestTemplate restTemplate;  // Paso 4.5: Declaración de una variable final de tipo RestTemplate

    private String apiUrl;  // Paso 4.6: Variable para almacenar la URL de la API
    @Getter
    private String apiKey;  // Paso 4.7: Variable para almacenar la clave de la API, con getter generado automáticamente por Lombok

    // Paso 4.8: Constructor de la clase HttpHelper
    public HttpHelper(ApiConfig apiConfig, RestTemplate restTemplate) {
        this.apiConfig = apiConfig;  // Inicializa apiConfig con el parámetro apiConfig
        this.restTemplate = restTemplate;  // Inicializa restTemplate con el parámetro restTemplate
    }

    // Paso 4.9: Metodo para configurar la API según el tipo de API
    public void configureApi(ApiType apiType) {
        this.apiUrl = apiConfig.getApiUrl(apiType);  // Asigna la URL de la API según el tipo
        this.apiKey = apiConfig.getApiKey(apiType);  // Asigna la clave de la API según el tipo
    }

    // Paso 4.10: Metodo para realizar una solicitud HTTP GET y obtener la respuesta
    public String getResponse(String urlString) {
        try {
            System.out.println("Realizando solicitud a: " + urlString);  // Imprime la URL de la solicitud
            return restTemplate.getForObject(urlString, String.class);  // Realiza la solicitud y devuelve la respuesta como String
        } catch (Exception e) {
            System.err.println("Error en la solicitud: " + e.getMessage());  // Imprime el mensaje de error si la solicitud falla
            return null;  // Devuelve null si hay un error
        }
    }

    /**
     * En caso de emplear lombok el metodo getApiUrl será transformada como apiUrl
     * por tanto se deberá cambiar getApiUrl en todos los métodos o clases
     * donde se emplee.
     */
    // Paso 4.11: Getter para obtener la URL de la API
    public String getApiUrl() {
        return apiUrl;  // Devuelve la URL de la API
    }
}
