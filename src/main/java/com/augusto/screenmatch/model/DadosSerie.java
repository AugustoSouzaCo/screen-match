package com.augusto.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo, // alias serve só pra ler(desserializar), property serve para os 2 processos
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("totalSeasons") Integer totalTemporadas, // É possível colocar um range de palavras pra ele procurar para desserializar
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Plot") String sinopse,
                         @JsonAlias("Poster") String poster){
    @Override
    public String toString() {
        return  "Título='" + titulo + '\'' +
                ", Gênero='" + genero + '\'' +
                ", Temporadas=" + totalTemporadas +
                ", Avaliação='" + avaliacao + '\'' +
                ", Atores='" + atores + '\'' +
                ", Sinopse='" + sinopse + '\'' +
                ", Poster='" + poster + '\'';
    }
}
