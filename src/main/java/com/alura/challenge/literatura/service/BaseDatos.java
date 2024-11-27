package com.alura.challenge.literatura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BaseDatos {

    @Autowired
    private LibroRepository libroRepository;  // Inyectamos el repositorio

    // Método para guardar un libro en la base de datos
    public LibroEntity guardarLibro(LibroEntity libro) {
        return libroRepository.save(libro);  // Guardamos el libro usando el repositorio
    }

    // Método para obtener todos los libros de la base de datos
    public List<LibroEntity> listarTodosLosLibros() {
        return libroRepository.findAll();  // Devuelve todos los libros en la base de datos
    }

    // Método para buscar un libro por su título
    public List<LibroEntity> buscarLibroPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);  // Busca por título
    }

    // Método para buscar libros por idioma
    public List<LibroEntity> buscarLibroPorIdioma(String idioma) {
        return libroRepository.findByIdiomaContainingIgnoreCase(idioma);  // Busca por idioma
    }

    // Método para buscar libros con más de un número específico de descargas
    public List<LibroEntity> buscarLibroPorDescargas(int descargas) {
        return libroRepository.findByNumeroDescargasGreaterThan(descargas);  // Busca por descargas
    }

    // Método para obtener un libro por su ID
    public Optional<LibroEntity> obtenerLibroPorId(Long id) {
        return libroRepository.findById(id);  // Busca por ID
    }

    public List<String> listarAutoresRegistrados() {
        // Obtener todos los libros y extraer los autores
        List<LibroEntity> libros = libroRepository.findAll();
        // Usar un Set para evitar duplicados
        Set<String> autores = new HashSet<>();

        for (LibroEntity libro : libros) {
            autores.addAll(libro.getAutores());  // Agregar todos los autores de cada libro
        }

        return new ArrayList<>(autores);  // Convertir el Set a lista para devolver
    }

    public List<LibroEntity> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdiomaContainingIgnoreCase(idioma);  // Realiza la búsqueda por idioma
    }



}
