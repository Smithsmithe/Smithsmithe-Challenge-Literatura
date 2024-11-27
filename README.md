# Challenge de Literatura One

Este proyecto es parte del desafío "Challenge de Literatura One" y tiene como objetivo gestionar información sobre libros y autores utilizando Spring Boot. Permite realizar varias operaciones como buscar libros por título, listar libros registrados, y más.

## Funcionalidades

El sistema proporciona un menú interactivo en consola con las siguientes opciones:

1. **Buscar libro por título**: Permite al usuario buscar un libro por su título. Si ya existe un libro con un título similar en la base de datos, se muestra la información del libro. Si no, se consulta una API externa para obtener la información del libro y guardarlo en la base de datos.

2. **Listar libros registrados**: Muestra todos los libros registrados en la base de datos.

3. **Listar autores registrados**: Muestra todos los autores que han sido registrados en los libros de la base de datos.

4. **Listar autores vivos en un determinado año**: Permite listar los autores que estaban vivos en un año específico. (Nota: Esta función aún no está implementada).

5. **Listar libros por idioma**: Permite listar todos los libros que están registrados en un idioma específico.

6. **Salir**: Cierra la aplicación.

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior
- Spring Boot 2.7.x
- Conexión a Internet (para consultar la API externa)

## Instalación

1. Clona este repositorio en tu máquina local:

   ```bash
   https://github.com/Smithsmithe/Smithsmithe-Challenge-Literatura.git

2. Navega al directorio del proyecto
    
    ```bashcd 
   challenge-literatura 

3. Instala las dependencias del proyecto con Maven:

    ```bashcd 
   mvn install 

4. Ejecuta la aplicación:

    ```bashcd 
   mvn spring-boot:run

5. Una vez ejecutada la aplicación, el menú interactivo aparecerá en la consola y podrás seleccionar las opciones disponibles.

## Estructura del Proyecto

El proyecto está estructurado de la siguiente manera:

- **com.alura.challenge.literatura.Principal**: Contiene la clase principal `Principal` que maneja el menú interactivo y las opciones.
- **com.alura.challenge.literatura.service**: Contiene los servicios para interactuar con la base de datos y la API externa.
- **com.alura.challenge.literatura.model**: Contiene las entidades y modelos de datos.

## Componentes principales

- **Principal**: Esta clase contiene el menú principal y maneja las interacciones con el usuario.
- **BaseDatos**: Esta clase interactúa con el repositorio de libros en la base de datos.
- **ConsumoAPI**: Realiza la consulta a la API externa para obtener los datos de los libros si no se encuentran en la base de datos.
- **ConvierteDatos**: Convierte la respuesta JSON de la API externa en objetos Java.

## Endpoints de la API externa

Este proyecto consume datos de la API de **Gutendex** para obtener información de libros. La URL base para las consultas es:

    https://gutendex.com/books/?search=

