package com.stin_city_taxi;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
	"com.taxi.external",
	"com.exceptionHandler"
})
@EnableJpaRepositories(basePackages = {
	"io.github.frame_code.domain.repository"
})
@Log
public class Main {
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

		SpringApplication.run(Main.class, args);
	}

}
