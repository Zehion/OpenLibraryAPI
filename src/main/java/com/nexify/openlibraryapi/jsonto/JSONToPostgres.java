package com.nexify.openlibraryapi.jsonto;  // Define el paquete al que pertenece esta clase

// Importaciones necesarias para esta clase
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Component  // Marca esta clase como un componente de Spring, lo que permite que Spring la gestione
public class JSONToPostgres {

    private final JdbcTemplate jdbcTemplate;  // Declaración de una variable final de tipo JdbcTemplate

    @Autowired  // Inyección automática de JdbcTemplate
    public JSONToPostgres(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;  // Inicializa jdbcTemplate con el parámetro proporcionado
    }

    @Transactional  // Marca este metodo como transaccional para asegurar la consistencia de los datos
    public void insertJsonData(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();  // Crea un nuevo ObjectMapper para trabajar con JSON
        JsonNode root = objectMapper.readTree(jsonData);  // Lee el JSON de los datos proporcionados
        JsonNode results = root.get("results");  // Obtiene el nodo "results" del JSON

        for (JsonNode book : results) {
            // Insertar autor
            JsonNode author = book.get("authors").get(0);  // Obtiene el nodo del primer autor
            String authorName = author.get("name").asText();  // Obtiene el nombre del autor
            int birthYear = author.path("birth_year").asInt(0);  // Obtiene el año de nacimiento del autor, por defecto 0
            int deathYear = author.path("death_year").asInt(0);  // Obtiene el año de fallecimiento del autor, por defecto 0

            Integer authorId = jdbcTemplate.queryForObject(
                    "INSERT INTO authors (name, birth_year, death_year) VALUES (?, ?, ?) RETURNING author_id",
                    new Object[]{authorName, birthYear, deathYear},
                    Integer.class  // Ejecuta la consulta para insertar el autor y obtener el ID generado
            );

            // Insertar libro
            Integer bookId = jdbcTemplate.queryForObject(
                    "INSERT INTO books (title, author_id, media_type, copyright, download_count) VALUES (?, ?, ?, ?, ?) RETURNING book_id",
                    new Object[]{book.get("title").asText(), authorId, book.get("media_type").asText(), book.get("copyright").asBoolean(), book.get("download_count").asInt()},
                    Integer.class  // Ejecuta la consulta para insertar el libro y obtener el ID generado
            );

            // Insertar temas
            JsonNode subjects = book.get("subjects");  // Obtiene el nodo "subjects"
            for (JsonNode subjectNode : subjects) {
                String subject = subjectNode.asText();  // Obtiene el nombre del tema
                Integer subjectId = jdbcTemplate.queryForObject(
                        "INSERT INTO subjects (name) VALUES (?) RETURNING subject_id",
                        new Object[]{subject},
                        Integer.class  // Ejecuta la consulta para insertar el tema y obtener el ID generado
                );

                jdbcTemplate.update(
                        "INSERT INTO book_subject (book_id, subject_id) VALUES (?, ?)",
                        bookId, subjectId  // Ejecuta la consulta para asociar el libro con el tema
                );
            }

            // Insertar estantes
            JsonNode bookshelves = book.get("bookshelves");  // Obtiene el nodo "bookshelves"
            for (JsonNode bookshelfNode : bookshelves) {
                String bookshelf = bookshelfNode.asText();  // Obtiene el nombre del estante
                Integer bookshelfId = jdbcTemplate.queryForObject(
                        "INSERT INTO bookshelves (name) VALUES (?) RETURNING bookshelf_id",
                        new Object[]{bookshelf},
                        Integer.class  // Ejecuta la consulta para insertar el estante y obtener el ID generado
                );

                jdbcTemplate.update(
                        "INSERT INTO book_bookshelf (book_id, bookshelf_id) VALUES (?, ?)",
                        bookId, bookshelfId  // Ejecuta la consulta para asociar el libro con el estante
                );
            }

            // Insertar formatos
            JsonNode formats = book.get("formats");  // Obtiene el nodo "formats"
            formats.fields().forEachRemaining(entry -> {
                String formatType = entry.getKey();  // Obtiene el tipo de formato
                String formatUrl = entry.getValue().asText();  // Obtiene la URL del formato
                jdbcTemplate.update(
                        "INSERT INTO formats (book_id, format_type, format_url) VALUES (?, ?, ?)",
                        bookId, formatType, formatUrl  // Ejecuta la consulta para insertar el formato
                );
            });

            // Insertar idiomas
            JsonNode languages = book.get("languages");  // Obtiene el nodo "languages"
            for (JsonNode languageNode : languages) {
                String language = languageNode.asText();  // Obtiene el código del idioma
                jdbcTemplate.update(
                        "INSERT INTO languages (book_id, language_code) VALUES (?, ?)",
                        bookId, language  // Ejecuta la consulta para insertar el idioma
                );
            }
        }
    }
}

