package com.stin_city_taxi;

import io.github.cdimascio.dotenv.Dotenv;
import junit.framework.TestCase;
import org.junit.Test;

public class MainTest extends TestCase {
    @Test
    public void testLoadEnvironmentVariables() {
        Dotenv dotenv = Dotenv.configure()
                .load();

        dotenv.entries().forEach(entry
                -> System.setProperty(entry.getKey(), entry.getValue())
        );

    }
  
}