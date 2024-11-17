# Proyecto OpenLibraryAPI

Este proyecto implementa una API que interactúa con el servicio de Gutendex para buscar y almacenar libros en una base de datos PostgreSQL. La aplicación está desarrollada utilizando Spring Boot y varias otras tecnologías para crear una solución completa y robusta para la gestión de datos de libros.

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación principal.
- **Spring Boot**: Framework para crear aplicaciones basadas en Spring de manera rápida y eficiente.
- **Spring Data JPA**: Para la interacción con la base de datos utilizando JPA (Java Persistence API).
- **PostgreSQL**: Sistema de gestión de bases de datos relacional.
- **Jakarta Persistence**: Para las anotaciones de JPA.
- **Lombok**: Biblioteca para reducir el código boilerplate.
- **Gutendex API**: Servicio utilizado para buscar libros.

## Funcionalidad del Programa

El programa permite realizar varias operaciones relacionadas con la búsqueda y gestión de libros:

1. **Buscar libros por título**: Permite buscar libros en la base de datos utilizando parte o todo el título del libro.
2. **Buscar libros por autor**: Permite buscar libros en la base de datos utilizando el nombre del autor.
3. **Buscar autores por año**: Permite buscar autores en la base de datos utilizando su año de nacimiento.
4. **Buscar libros por idioma**: Permite buscar libros en la base de datos utilizando el código del idioma.
5. **Buscar por página en Gutendex**: Permite buscar libros directamente desde la API de Gutendex y almacenar los resultados en la base de datos.

## Componentes del Proyecto

### Archivos de Configuración

- **application.properties**: Configuración de las propiedades de la aplicación, incluyendo la conexión a la base de datos y detalles de la API.
- **ApiConfig.java**: Configuración de las claves y URLs de las APIs.

### Enumeraciones

- **ApiType.java**: Define los diferentes tipos de API disponibles.

### Clases Auxiliares

- **HttpHelper.java**: Facilita las solicitudes HTTP a la API.

### Entidades

- **Author.java**: Define la entidad `Author` que representa a los autores en la base de datos.
- **Book.java**: Define la entidad `Book` que representa a los libros en la base de datos.

### Repositorios

- **IAuthorRepository.java**: Repositorio para la entidad `Author`.
- **IBookRepository.java**: Repositorio para la entidad `Book`.
- **IBookshelfRepository.java**: Repositorio para la entidad `Bookshelf`.

### Servicios

- **ApiService.java**: Contiene la lógica para interactuar con la API de Gutendex y la base de datos.

### Aplicación Principal

- **OpenLibraryApiApplication.java**: Punto de entrada de la aplicación, maneja el menú y las interacciones con el usuario.

## Ejemplo de Uso

1. **Iniciar la aplicación**:
   ```bash
   mvn spring-boot:run
