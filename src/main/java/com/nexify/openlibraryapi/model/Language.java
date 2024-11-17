package com.nexify.openlibraryapi.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language_code")
    private String languageCode;

    @ManyToMany(mappedBy = "languages")
    private List<Book> books;

    // Constructor vacío
    public Language() {}

    // Constructor con parámetros
    public Language(String languageCode, List<Book> books) {
        this.languageCode = languageCode;
        this.books = books;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

