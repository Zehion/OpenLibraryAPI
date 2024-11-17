package com.nexify.openlibraryapi.model;  // Define el paquete al que pertenece esta clase

// Importaciones necesarias para esta clase
import jakarta.persistence.*;  // Importa las anotaciones de JPA (Java Persistence API) necesarias

@Entity  // Marca esta clase como una entidad JPA que se mapea a una tabla en la base de datos
public class Format {

    @Id  // Indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Especifica que el valor de este campo será generado automáticamente por la base de datos
    private Long id;  // Identificador único para cada formato

    private String formatType;  // Tipo de formato (por ejemplo, EPUB, PDF, etc.)
    private String formatUrl;  // URL del formato donde se puede acceder o descargar el libro

    @ManyToOne  // Define una relación muchos a uno con la entidad Book
    @JoinColumn(name = "book_id")  // Especifica la columna que se usará para unir las tablas Format y Book
    private Book book;  // Referencia al libro asociado con este formato

    // Constructor vacío
    public Format() {}

    // Constructor con parámetros
    public Format(String formatType, String formatUrl, Book book) {
        this.formatType = formatType;  // Inicializa el tipo de formato
        this.formatUrl = formatUrl;  // Inicializa la URL del formato
        this.book = book;  // Inicializa la referencia al libro
    }

    // Getters y Setters para cada campo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getFormatUrl() {
        return formatUrl;
    }

    public void setFormatUrl(String formatUrl) {
        this.formatUrl = formatUrl;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
