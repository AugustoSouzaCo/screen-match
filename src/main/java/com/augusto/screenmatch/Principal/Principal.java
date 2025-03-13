package com.augusto.screenmatch.Principal;

import com.augusto.screenmatch.model.*;
import com.augusto.screenmatch.service.ConsumoApi;
import com.augusto.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "http://www.omdbapi.com/?t="; // constantes de endereco para requisição
    private final String API_KEY = "&apikey=d40064cb";
    private final String TEMPORADA = "&Season=";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Séries buscadas
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }
    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        dadosSeries.add(dados);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + TEMPORADA + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
    private void  listarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
       series = dadosSeries.stream()
                        .map(d -> new Serie(d))
                                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

}


















//    public void exibeMenu() {
//        System.out.println("Digite o nome da série para busca: ");
//        var nomeSerie = leitor.nextLine();
//        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);
//        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
////        System.out.println(dadosSerie);
//
//        List<DadosTemporada> temporadas = new ArrayList<>();
//        for (int i = 1; i<=dadosSerie.temporadas(); i++) { //Começa no 1 pq não tem temp 0
//            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + TEMPORADA + i + APIKEY);
//            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//            temporadas.add(dadosTemporada);
//        }
////        temporadas.forEach(System.out::println); // Method reference (forma curta de escrever uma expressão lambda,
//        // metodo forEach pertence a interface iterable
//
////        for (int i = 0; i < dadosSerie.temporadas(); i++) {
////            List<DadosEpisodio> listaDeEpisodios = temporadas.get(i).episodios();
////            for (int j = 0; j < listaDeEpisodios.size(); j++) {
////                System.out.println(listaDeEpisodios.get(j).titulo());
////            }
////        }
////        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//
////        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
////                .flatMap(t -> t.episodios().stream()) // possível juntar listas dentro de outras, recuperando todas de uma vez
////                .toList(); // Aqui a lista gerada é imutável
//////                        .collect(Collectors.toList()); // Aqui é mutável
////        System.out.println("\nEPISÓDIOS MELHORES AVALIADOS");
////        dadosEpisodios.stream()
////                .filter(dadoEp -> !dadoEp.avaliacao().equalsIgnoreCase("N/A"))
//////                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e)) Prática de DEBUG
////                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//////                .peek(e -> System.out.println("Ordenação " + e))
////                .limit(10)
//////                .peek(e -> System.out.println("Limite " + e))
////                .map(e -> e.titulo().toUpperCase())
//////                .peek(e -> System.out.println("Mapeamento " + e))
////                .forEach(System.out::println);
//
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodio(t.numero(),  d))
//                ).collect(Collectors.toList());
//        episodios.forEach(System.out::println);
//
//        System.out.println("Digite um trecho do título do episódio: ");
//
////        var trechoTitulo = leitor.nextLine();
////        Optional<Episodio> epBusca = episodios.stream() // Caso tenha uma referencia nula
////                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
////                .findFirst();
////        if (epBusca.isPresent()) {
////            System.out.println("Episódio encontrado na temporada " + epBusca.get().getTemporada());
////        } else {
////            System.out.println("Episódio não encontrado!");
////        }
////        System.out.println("A partir de que ano você deseja ver os episódios: ");
////        var ano = leitor.nextInt();
////        leitor.nextLine();
//
////        LocalDate dataBusca = LocalDate.of(ano, 1 ,1);
////        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////        episodios.stream()
////                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
////                .forEach(e -> System.out.println(
////                        "Temporada: " + e.getTemporada() +
////                                "; Episódio: " + e.getTitulo() +
////                                "; Número: " + e.getNumero() +
////                                "; Data de lançamento: " + e.getDataLancamento().format(formatadorData) // usar formatador aqui
////
////                ));
//
//        Map<Integer, Double> avalicoesTemporadas = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getAvaliacao)));
//        System.out.println(avalicoesTemporadas);
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e -> e.getAvaliacao() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
//        System.out.println("Média: " + est.getAverage());
//        System.out.println("Melhor episódio: " + est.getMax());
//        System.out.println("Pior episódio: " + est.getMin());
//        System.out.println("Quantidade: " + est.getCount());
//    }

