package com.nexify.openlibraryapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexify.openlibraryapi.helper.HttpHelper;
import com.nexify.openlibraryapi.model.*;
import com.nexify.openlibraryapi.nums.ApiType;
import com.nexify.openlibraryapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApiService {
    private final HttpHelper httpHelper;
    private final TransactionalHelper transactionalHelper;

    @Autowired
    public ApiService(HttpHelper httpHelper, @Lazy TransactionalHelper transactionalHelper) {
        this.httpHelper = httpHelper;
        this.transactionalHelper = transactionalHelper;
    }

    public TransactionalHelper getTransactionalHelper() {
        return transactionalHelper;
    }

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ISubjectRepository subjectRepository;

    @Autowired
    private IBookshelfRepository bookshelfRepository;

    @Autowired
    private IFormatRepository formatRepository;

    @Autowired
    private ILanguageRepository languageRepository;

    // Métodos existentes
    public List<Book> searchBooksByPartialName(String partialName) {
        return bookRepository.findByTitleContainingIgnoreCase(partialName);
    }

    @Transactional
    public String fetchBooksByTitle(String title) {
        httpHelper.configureApi(ApiType.GUTENDEX);
        String endpoint = "books/?search=" + title.replaceAll(" ", "%20");
        String url = httpHelper.getApiUrl() + endpoint;
        return httpHelper.getResponse(url);
    }

    @Transactional
    public void processAndStoreBooksByTitle(String title) {
        String jsonResponse = fetchBooksByTitle(title);
        transactionalHelper.saveJsonData(jsonResponse);
    }

    @Transactional
    public String fetchBooksByAuthorInternal(String authorName) {
        httpHelper.configureApi(ApiType.GUTENDEX);
        String endpoint = "books/?search=" + authorName.replaceAll(" ", "%20");
        String url = httpHelper.getApiUrl() + endpoint;
        return httpHelper.getResponse(url);
    }

    @Transactional
    public void processAndStoreBooksByAuthor(String authorName) {
        String jsonResponse = transactionalHelper.fetchBooksByAuthor(authorName);
        transactionalHelper.saveJsonData(jsonResponse);
    }


    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> books = bookRepository.findByAuthorNameContainingIgnoreCase(authorName);

        if (books.isEmpty()) {
            processBooksByAuthor(authorName);
            books = bookRepository.findByAuthorNameContainingIgnoreCase(authorName);
        }

        return books;
    }

    public void processBooksByAuthor(String authorName) {
        String jsonResponse = transactionalHelper.fetchBooksByAuthor(authorName);
        transactionalHelper.saveJsonData(jsonResponse);
    }

    @Transactional
    public String fetchBooksByPage(int page) {
        httpHelper.configureApi(ApiType.GUTENDEX);
        String endpoint = "books/?page=" + page;
        String url = httpHelper.getApiUrl() + endpoint;
        return httpHelper.getResponse(url);
    }

    @Transactional
    public void processAndStoreBooksByPage(int page) {
        String jsonResponse = fetchBooksByPage(page);
        transactionalHelper.saveJsonData(jsonResponse);
    }

    public List<Book> searchBooksByPage(int page) {
        // Metodo para buscar libros en la base de datos que fueron guardados al buscar por página
        // Implementa la lógica según tus necesidades de base de datos
        return bookRepository.findAll(); // Ejemplo: ajustar según sea necesario
    }

    @Transactional
    public String fetchBooksDetails(String titleOrAuthor) {
        httpHelper.configureApi(ApiType.GUTENDEX);
        String endpoint = "books/?search=" + titleOrAuthor.replaceAll(" ", "%20"); // Reemplazamos espacios por %20 para URL
        String url = httpHelper.getApiUrl() + endpoint;
        return httpHelper.getResponse(url);
    }

    @Transactional
    public String fetchFromApi(ApiType apiType, String endpoint) {
        httpHelper.configureApi(apiType);
        String url = httpHelper.getApiUrl() + endpoint;
        return httpHelper.getResponse(url);
    }

    @Transactional
    public List<Book> searchBooksInDatabase(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        books.forEach(book -> {
            book.getFormats().size(); // Carga de formatos
            book.getLanguages().size(); // Carga de idiomas
        });
        return books;
    }

    @Transactional
    public Optional<Book> searchBookById(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        bookOpt.ifPresent(book -> {
            book.getFormats().size(); // Carga de formatos
            book.getLanguages().size(); // Carga de idiomas
        });
        return bookOpt;
    }

    public List<Author> searchAuthorsByName(String authorName) {
        return authorRepository.findByNameContainingIgnoreCase(authorName);
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public Book saveBookAndReturn(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book getBookWithDetails(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        book.getFormats().size(); // Carga de formatos
        book.getLanguages().size(); // Carga de idiomas
        return book;
    }

    public List<Author> searchAuthorsByYear(String year) {
        return authorRepository.findByBirthYear(Integer.parseInt(year));
    }

    public List<Book> searchBooksByLanguage(String languageCode) {
        return bookRepository.findByLanguagesLanguageCode(languageCode); // Metodo para buscar libros por código de idioma
    }

    // Metodo para truncar cadenas de texto que excedan una longitud especificada
    private String truncateToLength(String value, int length) {
        if (value.length() > length) {
            return value.substring(0, length);
        } else {
            return value;
        }
    }

    @Transactional
    public void saveJsonDataInternal(String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(jsonData);
            JsonNode results = json.get("results");

            int bookNumber = 1;  // Contador para el número de libro

            for (JsonNode bookJson : results) {
                String bookTitle = truncateToLength(bookJson.get("title").asText(), 255);
                JsonNode authorsNode = bookJson.get("authors");
                if (authorsNode == null || authorsNode.isEmpty()) {
                    System.err.println("El libro número " + bookNumber + " ('" + bookTitle + "') no tiene autores disponibles.");
                    bookNumber++;  // Incrementa el contador del libro
                    continue;
                }
                String authorName = authorsNode.get(0).get("name").asText();

                Optional<Book> existingBook = bookRepository.findByTitleAndAuthorName(bookTitle, authorName);
                if (existingBook.isPresent()) {
                    System.out.println("El libro número " + bookNumber + " ('" + bookTitle + "') del autor '" + authorName + "' ya existe en la base de datos.");
                    bookNumber++;  // Incrementa el contador del libro
                    continue;
                }

                JsonNode authorJson = authorsNode.get(0);
                Author author = authorRepository.findByName(authorName).orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    newAuthor.setBirthYear(authorJson.path("birth_year").asInt(0));
                    newAuthor.setDeathYear(authorJson.path("death_year").asInt(0));
                    return authorRepository.save(newAuthor);
                });

                Book book = new Book();
                book.setTitle(bookTitle);
                book.setMediaType(truncateToLength(bookJson.get("media_type").asText(), 255));
                book.setCopyright(bookJson.get("copyright").asBoolean());
                book.setDownloadCount(bookJson.get("download_count").asInt());
                book.setAuthor(author);
                book = bookRepository.save(book);

                JsonNode subjectsJson = bookJson.get("subjects");
                if (subjectsJson != null) {
                    for (JsonNode subjectJson : subjectsJson) {
                        String subjectName = subjectJson.asText();
                        Subject subject = subjectRepository.findByName(subjectName).orElseGet(() -> {
                            Subject newSubject = new Subject();
                            newSubject.setName(subjectName);
                            return subjectRepository.save(newSubject);
                        });
                        book.getSubjects().add(subject);
                    }
                }

                JsonNode bookshelvesJson = bookJson.get("bookshelves");
                if (bookshelvesJson != null) {
                    for (JsonNode bookshelfJson : bookshelvesJson) {
                        String bookshelfName = bookshelfJson.asText();
                        Bookshelf bookshelf = bookshelfRepository.findByName(bookshelfName).orElseGet(() -> {
                            Bookshelf newBookshelf = new Bookshelf();
                            newBookshelf.setName(bookshelfName);
                            return bookshelfRepository.save(newBookshelf);
                        });
                        book.getBookshelves().add(bookshelf);
                    }
                }

                final Book finalBook = book;
                JsonNode formatsJson = bookJson.get("formats");
                if (formatsJson != null) {
                    formatsJson.fields().forEachRemaining(entry -> {
                        String formatType = entry.getKey();
                        String formatUrl = entry.getValue().asText();
                        Format format = new Format();
                        format.setFormatType(formatType);
                        format.setFormatUrl(formatUrl);
                        format.setBook(finalBook);
                        formatRepository.save(format);
                    });
                }

                JsonNode languagesJson = bookJson.get("languages");
                if (languagesJson != null) {
                    for (JsonNode languageJson : languagesJson) {
                        String languageCode = languageJson.asText();
                        Language language = languageRepository.findByLanguageCode(languageCode).stream().findFirst().orElseGet(() -> {
                            Language newLanguage = new Language();
                            newLanguage.setLanguageCode(languageCode);
                            newLanguage.setBooks(new ArrayList<>()); // Inicializar lista de libros
                            return languageRepository.save(newLanguage);
                        });
                        language.getBooks().add(book); // Inicializar dentro de una sesión
                        book.getLanguages().add(language);
                    }
                }

                bookRepository.save(book);
                // Incrementa el contador del libro después de cada iteración
                bookNumber++;
            }
        } catch (Exception e) {
            System.err.println("Error al guardar datos del JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

}