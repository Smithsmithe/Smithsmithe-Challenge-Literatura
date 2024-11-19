package com.alura.challenge.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosLibro(

       @JsonAlias("title") String titulo,

       @JsonAlias("authors.name") String autor,

        String idioma,

        Integer numeroDescargas

         ) {
}
