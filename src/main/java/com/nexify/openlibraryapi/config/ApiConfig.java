// Paso 2: Archivo: ApiConfig.java
// Define la configuración de las claves y URLs de las APIs.

package com.nexify.openlibraryapi.config;  // Paso 2.1: Define el paquete en el que se encuentra esta clase

// Paso 2.2: Importaciones necesarias para esta clase
import com.nexify.openlibraryapi.nums.ApiType;  // Enum para diferenciar tipos de API
import lombok.Setter;  // Lombok es una biblioteca que genera automáticamente los métodos setters
import org.springframework.beans.factory.annotation.Value;  // Para inyectar valores desde properties
import org.springframework.stereotype.Component;  // Marca esta clase como un componente Spring

import java.util.HashMap;  // Importa la clase HashMap para trabajar con mapas
import java.util.Map;  // Importa la interfaz Map

@Component  // Paso 2.3: Indica que esta clase es un bean de Spring y puede ser gestionada por el contenedor de Spring
public class ApiConfig {

    // Paso 2.4: Mapas para almacenar las claves y URLs de las APIs
    private final Map<ApiType, String> apiKeys = new HashMap<>();
    private final Map<ApiType, String> apiUrls = new HashMap<>();

    // Paso 2.5: Constructor de la clase, inyecta las propiedades definidas en application.properties
    public ApiConfig(
            @Value("${gutendex.api.key}") String gutendexApiKey,  // Inyecta el valor de la propiedad gutendex.api.key
            @Value("${gutendex.api.url}") String gutendexApiUrl  // Inyecta el valor de la propiedad gutendex.api.url
    ) {
        // Paso 2.6: Almacena las claves y URLs en los mapas usando el tipo de API GUTENDEX
        apiKeys.put(ApiType.GUTENDEX, gutendexApiKey);
        apiUrls.put(ApiType.GUTENDEX, gutendexApiUrl);
    }

    @Setter  // Paso 2.7: Genera automáticamente el metodo setter para defaultApiType usando Lombok
    private ApiType defaultApiType = ApiType.GUTENDEX;  // Variable que define el tipo de API por defecto

    // Paso 2.8: Metodo para obtener la clave API según el tipo de API
    public String getApiKey(ApiType apiType) {
        return apiKeys.get(apiType);
    }

    // Paso 2.9: Metodo para obtener la URL de la API según el tipo de API
    public String getApiUrl(ApiType apiType) {
        return apiUrls.get(apiType);
    }
}
