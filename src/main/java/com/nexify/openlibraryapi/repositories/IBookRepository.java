// Paso 7: Archivo: IBookRepository.java
// Este archivo define el repositorio para la entidad Book.

package com.nexify.openlibraryapi.repositories;  // Paso 7.1: Define el paquete al que pertenece esta interfaz

// Paso 7.2: Importaciones necesarias para esta interfaz
import com.nexify.openlibraryapi.model.Book;  // Importa la clase Book
import org.springframework.data.jpa.repository.JpaRepository;  // Importa la interfaz JpaRepository de Spring Data JPA
import org.springframework.stereotype.Repository;  // Importa la anotación Repository

import java.util.List;  // Importa la clase List para manejar listas de libros
import java.util.Optional;  // Importa la clase Optional para manejar resultados que pueden no estar presentes

@Repository  // Paso 7.3: Marca esta interfaz como un repositorio de Spring, permitiendo que Spring gestione su implementación
public interface IBookRepository extends JpaRepository<Book, Long> {

    // Paso 7.4: Metodo para encontrar libros por su título (ignorando mayúsculas)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Paso 7.5: Metodo para encontrar libros por el nombre del autor (ignorando mayúsculas)
    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);

    // Paso 7.6: Metodo para encontrar un libro por su título y el nombre del autor
    Optional<Book> findByTitleAndAuthorName(String title, String authorName);

    // Paso 7.7: Metodo para encontrar libros por el código de idioma
    List<Book> findByLanguagesLanguageCode(String languageCode);
}


