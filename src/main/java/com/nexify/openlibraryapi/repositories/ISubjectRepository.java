package com.nexify.openlibraryapi.repositories;  // Define el paquete al que pertenece esta interfaz

// Importaciones necesarias para esta interfaz
import com.nexify.openlibraryapi.model.Subject;  // Importa la clase Subject
import org.springframework.data.jpa.repository.JpaRepository;  // Importa la interfaz JpaRepository de Spring Data JPA
import org.springframework.stereotype.Repository;  // Importa la anotación Repository

import java.util.Optional;  // Importa la clase Optional

@Repository  // Marca esta interfaz como un repositorio de Spring, lo que permite que Spring gestione su implementación
public interface ISubjectRepository extends JpaRepository<Subject, Long> {

    // Metodo para encontrar un sujeto por su nombre
    Optional<Subject> findByName(String name);
}

