package com.alura.challenge.literatura.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, Long> {

    // Buscar libro por título ignorando mayúsculas y minúsculas
    List<LibroEntity> findByTituloContainingIgnoreCase(String titulo);

    // Buscar libros por idioma ignorando mayúsculas y minúsculas
    List<LibroEntity> findByIdiomaContainingIgnoreCase(String idioma);

    // Buscar libros con un número mayor de descargas
    List<LibroEntity> findByNumeroDescargasGreaterThan(int descargas);

}
