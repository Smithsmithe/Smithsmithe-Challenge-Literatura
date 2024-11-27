package com.alura.challenge.literatura.Principal;

import com.alura.challenge.literatura.model.DatosLibroResponse;
import com.alura.challenge.literatura.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private BaseDatos baseDatos;  // Inyectamos la clase BaseDatos para usar su método guardarLibro

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    // Método para mostrar el menú en consola
    public void muestraMenu() {
        Scanner scanner = new Scanner(System.in); // Para leer la entrada del usuario

        int opcion;

        do {
            // Mostrar el menú
            System.out.println("---------------------------------");
            System.out.println("Por favor, digite el numero para seleccionar una opción");
            System.out.println("1- Buscar libro por título");
            System.out.println("2- Listar libros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos en un determinado año");
            System.out.println("5- Listar libros por idioma");
            System.out.println("0- Salir");
            System.out.println("---------------------------------");

            // Leer la opción seleccionada
            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            // Procesar la opción seleccionada
            switch (opcion) {
                case 1:
                    // Opción 1: Buscar libro por título
                    System.out.print("Ingrese el nombre del libro que desea buscar: ");
                    String titulo = scanner.nextLine();  // Leer el nombre del libro
                    buscarLibroPorTitulo(titulo); // Llamar al método de búsqueda
                    break;

                case 2:
                    // Opción 2: Listar libros registrados
                    listarLibrosRegistrados();
                    break;

                case 3:
                    // Opción 3: Listar autores registrados
                    listarAutoresRegistrados();
                    break;

                case 4:
                    // Opción 4: Listar autores vivos en un determinado año
                    System.out.print("Ingrese el año: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine();  // Limpiar el buffer
                    listarAutoresVivosEnAno(ano);
                    break;

                case 5:
                    // Opción 5: Listar libros por idioma
                    System.out.print("Ingrese el idioma: ");
                    String idioma = scanner.nextLine();
                    listarLibrosPorIdioma(idioma);
                    break;

                case 0:
                    // Opción 0: Salir
                    System.out.println("Gracias por usar LiteratuApp");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }

        } while (opcion != 0);  // El ciclo continua hasta que se elige la opción de salir
    }

    @Transactional
    public void buscarLibroPorTitulo(String titulo) {
        // Obtener todos los libros de la base de datos
        var libros = libroRepository.findAll();

        // Calcular similitud con los títulos en la base de datos
        var similarLibro = libros.stream()
                .filter(libro -> calcularSimilitud(libro.getTitulo(), titulo) > 0.6)
                .findFirst();
        System.out.println(similarLibro);

        if (similarLibro.isPresent()) {
            // Si hay un título similar, mostrar mensaje
            var libro = similarLibro.get();
            System.out.println("El libro ingresado tiene un título similar al ya registrado:");
            System.out.println("Título registrado: " + libro.getTitulo());
            libro.getAutores().forEach(autor -> System.out.println("Autor(es): " + autor));
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de descargas: " + libro.getNumeroDescargas());
        } else {
            // Si no hay similitudes, consultar la API y guardar el libro
            System.out.println("Buscando el libro " + titulo + " Por favor espere...");
            String url = URL_BASE + titulo.replace(" ", "%20");
            String json = consumoAPI.obtenerDatos(url);

            if (json == null) {
                System.out.println("Hubo un error al realizar la solicitud a la API.");
                return;
            }

            var datos = conversor.obtenerDatos(json, DatosLibroResponse.class);

            if (datos != null && !datos.libro().isEmpty()) {
                var libroApi = datos.libro().get(0);

                // Crear la entidad
                LibroEntity libroEntity = new LibroEntity();
                libroEntity.setTitulo(libroApi.titulo());
                libroEntity.setAutores(libroApi.autores().stream().map(autor -> autor.nombre()).toList());
                libroEntity.setIdioma(String.join(", ", libroApi.idioma()));
                libroEntity.setNumeroDescargas(libroApi.numeroDescargas());

                // Guardar en la base de datos
                libroRepository.save(libroEntity);

                System.out.println("Libro guardado en la base de datos.");
                System.out.println("Título: " + libroApi.titulo());
                libroApi.autores().forEach(autor -> System.out.println("Autor(es): " + autor.nombre()));
                System.out.println("Idioma: " + String.join(", ", libroApi.idioma()));
                System.out.println("Número de descargas: " + libroApi.numeroDescargas());
            } else {
                System.out.println("No se encontraron libros en la respuesta de la API.");
            }
        }
    }

    // Método para calcular similitud entre dos cadenas
    private double calcularSimilitud(String str1, String str2) {
        org.apache.commons.text.similarity.JaroWinklerSimilarity similarity =
                new org.apache.commons.text.similarity.JaroWinklerSimilarity();
        return similarity.apply(str1, str2); // Devuelve un valor entre 0 y 1
    }

    private void listarLibrosRegistrados() {
        System.out.println("Listado de libros registrados...");
        List<LibroEntity> libros = baseDatos.listarTodosLosLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                libro.getAutores().forEach(autor -> System.out.println("Autor(es): " + autor));
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("---------------------------------");
            });
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("Listado de autores registrados...");
        List<String> autores = baseDatos.listarAutoresRegistrados();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(autor -> System.out.println("Autor: " + autor));
        }
    }

    private void listarAutoresVivosEnAno(int ano) {
        System.out.println("Listado de autores vivos en el año: " + ano);
        System.out.println("logica no implementada");
    }

    private void listarLibrosPorIdioma(String idioma) {
        System.out.println("Listado de libros en el idioma: " + idioma);
        List<LibroEntity> libros = baseDatos.listarLibrosPorIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma);
        } else {
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                libro.getAutores().forEach(autor -> System.out.println("Autor(es): " + autor));
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("----------------------------");
            });
        }
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.muestraMenu();  // Llamar al método para mostrar el menú
    }
}
