package com.nexify.openlibraryapi.repositories;  // Define el paquete al que pertenece esta interfaz

// Importaciones necesarias para esta interfaz
import com.nexify.openlibraryapi.model.Format;  // Importa la clase Format
import org.springframework.data.jpa.repository.JpaRepository;  // Importa la interfaz JpaRepository de Spring Data JPA

public interface IFormatRepository extends JpaRepository<Format, Long> {
    // Esta interfaz hereda los métodos CRUD de JpaRepository para la entidad Format
}

