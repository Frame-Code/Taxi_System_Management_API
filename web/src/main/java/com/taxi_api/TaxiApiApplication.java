package com.taxi_api;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.frame_code.domain.entities.City;
import io.github.frame_code.domain.entities.Province;
import io.github.frame_code.domain.repository.CityRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.Level;

@SpringBootApplication
@EntityScan(basePackages = {
	"io.github.frame_code.domain.entities"
})
@ComponentScan(basePackages = {
	"com.controllers",
	"com.taxi.service",
	"com.taxi.external"
})
@EnableJpaRepositories(basePackages = {
	"io.github.frame_code.domain.repository"
})
@Log
public class TaxiApiApplication {
	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.configure()
					.load();

			dotenv.entries().forEach(entry
					-> System.setProperty(entry.getKey(), entry.getValue())
			);
			log.log(Level.INFO, "Environment variables loaded correctly");
		} catch (Exception e) {
			log.log(Level.SEVERE, "Error loading environment variables");
			System.exit(1);
		}

		SpringApplication.run(TaxiApiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CityRepository cityRepository) {
		return args -> {
			Province province1 = Province.builder()
					.name("Guayas")
					.build();
			City city1 = City.builder()
					.name("Durán")
					.province(province1)
					.build();

			cityRepository.save(city1);
			log.log(Level.INFO, "City saved");
		};
    }

}
