// Paso 5: Archivo: IAuthorRepository.java
// Este archivo define el repositorio para la entidad Author.

package com.nexify.openlibraryapi.repositories;  // Paso 5.1: Define el paquete al que pertenece esta interfaz

// Paso 5.2: Importaciones necesarias para esta interfaz
import com.nexify.openlibraryapi.model.Author;  // Importa la clase Author
import org.springframework.data.jpa.repository.JpaRepository;  // Importa la interfaz JpaRepository de Spring Data JPA
import org.springframework.stereotype.Repository;  // Importa la anotación Repository

import java.util.List;  // Importa la clase List para manejar listas de autores
import java.util.Optional;  // Importa la clase Optional para manejar resultados que pueden no estar presentes

@Repository  // Paso 5.3: Marca esta interfaz como un repositorio de Spring, permitiendo que Spring gestione su implementación
public interface IAuthorRepository extends JpaRepository<Author, Long> {

    // Paso 5.4: Metodo para encontrar autores por su nombre (ignorando mayúsculas)
    List<Author> findByNameContainingIgnoreCase(String name);

    // Paso 5.5: Metodo para encontrar autores por su año de nacimiento
    List<Author> findByBirthYear(int birthYear);

    // Paso 5.6: Metodo para encontrar un autor por su nombre
    Optional<Author> findByName(String name);
}
