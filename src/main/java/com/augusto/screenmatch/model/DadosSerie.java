package com.augusto.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo, // alias serve só pra ler(desserializar), property serve para os 2 processos
                         @JsonAlias("totalSeasons") Integer temporadas, // É possível colocar um range de palavras pra ele procurar para desserializar
                         @JsonAlias("imdbRating") String avaliacao){

}
