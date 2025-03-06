package com.augusto.screenmatch;

import com.augusto.screenmatch.model.DadosEpisodio;
import com.augusto.screenmatch.model.DadosSerie;
import com.augusto.screenmatch.model.DadosTemporada;
import com.augusto.screenmatch.service.ConsumoApi;
import com.augusto.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=breaking+bad&apikey=d40064cb");

		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);

		System.out.println(dadosSerie);

		json = consumoApi.obterDados("http://www.omdbapi.com/?t=breaking+bad&Season=1&episode=2&apikey=d40064cb");
		DadosEpisodio dadosEp = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEp);

		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i<=dadosSerie.temporadas(); i++) { //Começa no 1 pq não tem temp 0
			json = consumoApi.obterDados("http://www.omdbapi.com/?t=breaking+bad&Season=" + i + "&apikey=d40064cb");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println); // Method reference (forma curta de escrever uma expressão lambda,
		// metodo forEach pertence a interface iterable
	}
}
