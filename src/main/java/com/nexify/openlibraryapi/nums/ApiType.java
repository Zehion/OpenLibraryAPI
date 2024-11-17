// Paso 3: Archivo: ApiType.java
// Este archivo define una enumeración para los diferentes tipos de API disponibles.

package com.nexify.openlibraryapi.nums;  // Paso 3.1: Define el paquete al que pertenece esta enumeración

/**
 * Una enumeración (enum) en Java es un tipo especial de clase que representa un grupo fijo de constantes.
 * Las enumeraciones se utilizan para definir un conjunto de valores constantes que son conocidos en tiempo de compilación.
 * Es útil para representar datos que no cambian, como los días de la semana, direcciones de puntos cardinales, etc.
 * En este ejemplo, ApiType es una enumeración con un solo valor: GUTENDEX.
 * Esto se usa para identificar de manera clara y segura diferentes tipos de API dentro de la aplicación.
 */
// Paso 3.2: Definición de la enumeración ApiType
public enum ApiType {
    GUTENDEX,  // Representa la API de Gutendex
    OTHER_API  // Representa otra API que se pueda agregar en el futuro
}

