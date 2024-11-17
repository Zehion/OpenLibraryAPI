package com.nexify.openlibraryapi.model;  // Define el paquete al que pertenece esta clase

// Importaciones necesarias para esta clase
import jakarta.persistence.*;  // Importa las anotaciones de JPA (Java Persistence API) necesarias
import java.util.List;  // Importa la clase List para manejar listas de libros

@Entity  // Marca esta clase como una entidad JPA que se mapea a una tabla en la base de datos
public class Bookshelf {

    @Id  // Indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Especifica que el valor de este campo será generado automáticamente por la base de datos
    private Long id;  // Identificador único para cada estante de libros

    private String name;  // Nombre del estante de libros

    @ManyToMany(mappedBy = "bookshelves")  // Define una relación muchos a muchos con la entidad Book
    private List<Book> books;  // Lista de libros asociados con el estante de libros

    // Constructor vacío
    public Bookshelf() {}

    // Constructor con parámetros
    public Bookshelf(String name) {
        this.name = name;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
