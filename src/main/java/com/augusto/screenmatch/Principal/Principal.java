package com.augusto.screenmatch.Principal;

import com.augusto.screenmatch.model.DadosEpisodio;
import com.augusto.screenmatch.model.DadosSerie;
import com.augusto.screenmatch.model.DadosTemporada;
import com.augusto.screenmatch.model.Episodio;
import com.augusto.screenmatch.service.ConsumoApi;
import com.augusto.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitor = new Scanner(System.in);
    private final String ENDERECO = "http://www.omdbapi.com/?t="; // constantes de endereco para requisição
    private final String APIKEY = "&apikey=d40064cb";
    private final String TEMPORADA = "&Season=";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitor.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i<=dadosSerie.temporadas(); i++) { //Começa no 1 pq não tem temp 0
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + TEMPORADA + i + APIKEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println); // Method reference (forma curta de escrever uma expressão lambda,
        // metodo forEach pertence a interface iterable

//        for (int i = 0; i < dadosSerie.temporadas(); i++) {
//            List<DadosEpisodio> listaDeEpisodios = temporadas.get(i).episodios();
//            for (int j = 0; j < listaDeEpisodios.size(); j++) {
//                System.out.println(listaDeEpisodios.get(j).titulo());
//            }
//        }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()) // possível juntar listas dentro de outras, recuperando todas de uma vez
                .toList(); // Aqui a lista gerada é imutável
//                        .collect(Collectors.toList()); // Aqui é mutável
        System.out.println("\nEPISÓDIOS MELHORES AVALIADOS");
        dadosEpisodios.stream()
                .filter(dadoEp -> !dadoEp.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),  d))
                ).collect(Collectors.toList());
        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios: ");
        var ano = leitor.nextInt();
        leitor.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1 ,1);
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                "; Episódio: " + e.getTitulo() +
                                "; Número: " + e.getNumero() +
                                "; Data de lançamento: " + e.getDataLancamento().format(formatadorData) // usar formatador aqui

                ));
    }
}
