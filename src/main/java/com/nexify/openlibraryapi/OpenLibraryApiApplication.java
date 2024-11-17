package com.nexify.openlibraryapi;

import com.nexify.openlibraryapi.model.Book;
import com.nexify.openlibraryapi.model.Format;
import com.nexify.openlibraryapi.model.Language;
import com.nexify.openlibraryapi.model.Author;
import com.nexify.openlibraryapi.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = "com.nexify.openlibraryapi")
public class OpenLibraryApiApplication implements CommandLineRunner {

    @Autowired
    private ApiService apiService;

    public static void main(String[] args) {
        SpringApplication.run(OpenLibraryApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueApp = true;

            while (continueApp) {
                // Mostrar menú principal
                System.out.println("Menú Principal:");
                System.out.println("1. Buscar libros por título");
                System.out.println("2. Buscar libros por autor");
                System.out.println("3. Buscar autores por año");
                System.out.println("4. Buscar libros por idioma");
                System.out.println("5. Buscar por página en Gutendex");
                System.out.println("6. Salir");

                System.out.println("Selecciona una opción:");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        searchBooksByTitle(scanner);
                        break;
                    case "2":
                        searchBooksByAuthor(scanner);
                        break;
                    case "3":
                        searchAuthorsByYear(scanner);
                        break;
                    case "4":
                        searchBooksByLanguage(scanner);
                        break;
                    case "5":
                        searchBooksByPage(scanner);
                        break;
                    case "6":
                        continueApp = false;
                        System.out.println("Gracias por usar la aplicación.");
                        break;
                    default:
                        System.out.println("Opción inválida. Inténtalo de nuevo.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void searchBooksByTitle(Scanner scanner) {
        System.out.println("Introduce una parte del nombre del libro o el nombre completo:");
        String partialName = scanner.nextLine();

        List<Book> books = apiService.searchBooksByPartialName(partialName);

        if (books.isEmpty()) {
            apiService.processAndStoreBooksByTitle(partialName);
            books = apiService.searchBooksByPartialName(partialName);
        }

        if (books.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            displayBooks(scanner, books);
        }
    }

    private void searchBooksByAuthor(Scanner scanner) {
        System.out.println("Introduce el nombre del autor:");
        String authorName = scanner.nextLine();

        // Primero, realizar búsqueda en la API y almacenar los resultados
        apiService.processAndStoreBooksByAuthor(authorName);

        // Luego, realizar búsqueda en la base de datos local
        List<Book> books = apiService.searchBooksByAuthor(authorName);

        if (books.isEmpty()) {
            System.out.println("No se encontraron libros para el autor '" + authorName + "'.");
        } else {
            displayBooks(scanner, books);
        }
    }

    private void displayBooks(Scanner scanner, List<Book> books) {
        System.out.println("Se encontraron los siguientes libros:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i).getTitle() + " - " + books.get(i).getAuthor().getName());
        }

        System.out.println("Selecciona el número del libro que te interesa:");
        try {
            int selection = Integer.parseInt(scanner.nextLine());
            if (selection < 1 || selection > books.size()) {
                System.out.println("Número inválido. Inténtalo de nuevo.");
                return;
            }

            Book selectedBook = books.get(selection - 1);
            Book detailedBook = apiService.getBookWithDetails(selectedBook.getId());

            System.out.println("Datos del libro guardados exitosamente:");
            System.out.println("Título: " + detailedBook.getTitle());
            System.out.println("Autor: " + detailedBook.getAuthor().getName());
            System.out.println("Tipo de medio: " + detailedBook.getMediaType());
            System.out.println("Derechos de autor: " + (detailedBook.getCopyright() ? "Sí" : "No"));
            System.out.println("Número de descargas: " + detailedBook.getDownloadCount());
            System.out.println("Formatos disponibles:");
            for (Format format : detailedBook.getFormats()) {
                System.out.println(" - Tipo: " + format.getFormatType() + ", URL: " + format.getFormatUrl());
            }
            System.out.println("Idiomas disponibles:");
            for (Language language : detailedBook.getLanguages()) {
                System.out.println(" - Código del idioma: " + language.getLanguageCode());
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
        }
    }



    private void searchAuthorsByYear(Scanner scanner) {
        System.out.println("Introduce el año:");
        String year = scanner.nextLine();

        List<Author> authors = apiService.searchAuthorsByYear(year);

        if (authors.isEmpty()) {
            System.out.println("No se encontraron autores para ese año.");
        } else {
            displayAuthors(scanner, authors);
        }
    }

    private void searchBooksByLanguage(Scanner scanner) {
        System.out.println("Introduce el código del idioma (por ejemplo, 'en' para inglés):");
        String languageCode = scanner.nextLine();

        List<Book> books = apiService.searchBooksByLanguage(languageCode);

        if (books.isEmpty()) {
            String jsonResponse = apiService.fetchBooksDetails(languageCode);
            apiService.getTransactionalHelper().saveJsonData(jsonResponse); // Usamos TransactionalHelper para la transacción
            books = apiService.searchBooksByLanguage(languageCode);
        }

        if (books.isEmpty()) {
            System.out.println("No se encontraron libros para el idioma especificado.");
        } else {
            displayBooks(scanner, books);
        }
    }

    private void searchBooksByPage(Scanner scanner) {
        System.out.println("Introduce el número de página:");
        int page = Integer.parseInt(scanner.nextLine());

        // Realizar búsqueda en la API y almacenar los resultados
        apiService.processAndStoreBooksByPage(page);

        // Realizar búsqueda en la base de datos local
        List<Book> books = apiService.searchBooksByPage(page);

        if (books.isEmpty()) {
            System.out.println("No se encontraron libros en la página " + page + ".");
        } else {
            displayBooks(scanner, books);
        }

        System.out.println("Selecciona una opción:");
        System.out.println("1. Ver más libros");
        System.out.println("2. Volver al menú principal");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                searchBooksByPage(scanner); // Permitir buscar en otra página
                break;
            case "2":
                break;
            default:
                System.out.println("Opción inválida. Inténtalo de nuevo.");
        }
    }


    private void displayAuthors(Scanner scanner, List<Author> authors) {
        System.out.println("Se encontraron los siguientes autores:");
        for (int i = 0; i < authors.size(); i++) {
            System.out.println((i + 1) + ". " + authors.get(i).getName());
        }

        System.out.println("Selecciona el número del autor que te interesa:");
        try {
            int selection = Integer.parseInt(scanner.nextLine());
            if (selection < 1 || selection > authors.size()) {
                System.out.println("Número inválido. Inténtalo de nuevo.");
                return;
            }

            Author selectedAuthor = authors.get(selection - 1);
            List<Book> books = apiService.searchBooksByAuthor(selectedAuthor.getName());

            if (books.isEmpty()) {
                System.out.println("No se encontraron libros para este autor.");
            } else {
                displayBooks(scanner, books);
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
        }
    }
}


