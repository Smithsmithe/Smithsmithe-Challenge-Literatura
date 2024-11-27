package com.alura.challenge.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosLibroResponse(

        @JsonAlias("results") List<Libro> libro
) {
}