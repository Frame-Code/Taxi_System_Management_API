package io.github.frame_code.domain.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.logging.Level;

@Configuration
@EnableJpaRepositories(basePackages = "org.springframework.data.jpa.repository")
@EntityScan(basePackages = "org.springframework.data.jpa.entities")
@Log
public class TestJPAConfig {

    @Bean
    public DataSource dataSource() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .load();

            dotenv.entries().forEach(entry
                    -> System.setProperty(entry.getKey(), entry.getValue())
            );
            log.log(Level.INFO, "Environment variables loaded correctly");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error loading environment variables");
            System.exit(-1);
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(System.getProperty("DB_URL"));
        dataSource.setUsername(System.getProperty("DB_USERNAME"));
        dataSource.setPassword(System.getProperty("DB_PASSWORD"));
        return dataSource;
    }
}
