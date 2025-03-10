package com.augusto.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada = 0;
    private String titulo = "";
    private Integer numero = 0;
    private double avaliacao = 0.0;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemp, DadosEpisodio dadosEp) {
        this.temporada = numeroTemp;
        this.titulo = dadosEp.titulo();
        this.numero = dadosEp.numero();
        try {
            this.avaliacao = Double.valueOf(dadosEp.avaliacao());
        } catch (NumberFormatException e) {
            avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEp.dataLancamento());
        } catch (DateTimeParseException e) {
            dataLancamento = null;
        }

    }

    @Override
    public String toString() {
        return  "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numero=" + numero +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
}
