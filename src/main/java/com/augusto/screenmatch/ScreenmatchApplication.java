package com.augusto.screenmatch;

import com.augusto.screenmatch.Principal.Principal;
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
		Principal principal = new Principal();
		principal.exibeMenu();

	}
}
