package com.alura.challenge.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Libro(

        @JsonAlias("title") String titulo,  // Mapea el título del libro

        @JsonAlias("authors") List<Autor> autores,  // Mapea los autores (debe ser una lista de objetos Autor)

        @JsonAlias("languages") List<String> idioma,  // Mapea los idiomas (debe ser una lista de Strings)

        @JsonAlias("download_count") Integer numeroDescargas  // Mapea el número de descargas

) {}
