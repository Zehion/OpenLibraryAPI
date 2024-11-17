// Paso 6: Archivo: Author.java
// Este archivo define la clase Author, que representa a los autores en la base de datos.

package com.nexify.openlibraryapi.model;  // Paso 6.1: Define el paquete al que pertenece esta clase

// Paso 6.2: Importaciones necesarias para esta clase
import jakarta.persistence.*;  // Importa las anotaciones de JPA (Java Persistence API) necesarias
import java.util.List;  // Importa la clase List para manejar listas de libros

@Entity  // Paso 6.3: Marca esta clase como una entidad JPA que se mapea a una tabla en la base de datos
public class Author {

    @Id  // Paso 6.4: Indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Especifica que el valor de este campo será generado automáticamente por la base de datos
    private Long id;  // Identificador único para cada autor

    private String name;  // Nombre del autor
    private int birthYear;  // Año de nacimiento del autor
    private int deathYear;  // Año de fallecimiento del autor

    @OneToMany(mappedBy = "author")  // Define una relación uno a muchos con la entidad Book
    private List<Book> books;  // Lista de libros asociados con el autor

    // Constructor vacío
    public Author() {}

    // Constructor con parámetros
    public Author(String name, int birthYear, int deathYear) {
        this.name = name;  // Inicializa el nombre del autor
        this.birthYear = birthYear;  // Inicializa el año de nacimiento del autor
        this.deathYear = deathYear;  // Inicializa el año de fallecimiento del autor
    }

    // Getters y Setters para cada campo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}


