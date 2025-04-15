package com.taxi_api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.logging.Level;
import java.util.logging.Logger;

@EntityScan(basePackages = "io.github.frame_code.domain.entities")
@SpringBootApplication
public class TaxiApiApplication {
	private static final Logger LOG = Logger.getLogger(TaxiApiApplication.class.getName());

	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.configure()
					.load();

			dotenv.entries().forEach(entry
					-> System.setProperty(entry.getKey(), entry.getValue())
			);
			LOG.log(Level.INFO, "Environment variables loaded correctly!");
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error loading .env: ".concat(e.getMessage()));
			System.exit(1);
		}

		SpringApplication.run(TaxiApiApplication.class, args);
	}

}
