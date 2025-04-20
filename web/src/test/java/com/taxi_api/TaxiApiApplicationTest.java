package com.taxi_api;

import io.github.cdimascio.dotenv.Dotenv;
import junit.framework.TestCase;
import org.junit.Test;

public class TaxiApiApplicationTest extends TestCase {
    @Test
    public void testLoadEnvironmentVariables() {
        Dotenv dotenv = Dotenv.configure()
                .load();

        dotenv.entries().forEach(entry
                -> System.setProperty(entry.getKey(), entry.getValue())
        );

    }
  
}