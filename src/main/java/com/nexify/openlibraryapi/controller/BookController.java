package com.nexify.openlibraryapi.controller;  // Define el paquete al que pertenece esta clase

// Importaciones necesarias para esta clase
import com.nexify.openlibraryapi.model.Book;  // Importa la clase Book
import com.nexify.openlibraryapi.nums.ApiType;  // Importa la enumeración ApiType
import com.nexify.openlibraryapi.service.ApiService;  // Importa la clase ApiService
import org.springframework.beans.factory.annotation.Autowired;  // Importa la anotación Autowired para inyección de dependencias
import org.springframework.web.bind.annotation.*;  // Importa anotaciones necesarias para definir controladores y endpoints

import java.util.List;  // Importa la clase List para manejar listas de libros

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private ApiService apiService;

    public BookController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/books")
    public String getBook(@RequestParam String title, @RequestParam ApiType apiType) {
        String endpoint = "/?search=" + title;
        return apiService.fetchFromApi(apiType, endpoint);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title) {
        List<Book> localBooks = apiService.searchBooksInDatabase(title);
        if (!localBooks.isEmpty()) {
            return localBooks;
        } else {
            apiService.processAndStoreBooksByTitle(title);
            return apiService.searchBooksInDatabase(title);
        }
    }

    @GetMapping("/search/")
    public List<Book> searchBooksByPartialName(@RequestParam String partialName) {
        return apiService.searchBooksInDatabase(partialName);
    }

    @PostMapping("/save-selected")
    public Book saveSelectedBook(@RequestBody Book book) {
        return apiService.saveBookAndReturn(book);
    }

    @PostMapping("/save")
    public void saveBook(@RequestBody Book book) {
        apiService.saveBook(book);
    }

    @GetMapping("/{id}")
    public Book getBookDetails(@PathVariable Long id) {
        return apiService.searchBookById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
