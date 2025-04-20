package com.taxi.external.client.openCage;

import org.junit.Test;

public class OpenCageClientTest {
    OpenCageClient openCageClient = new OpenCageClient();

    @Test
    public void testReverse_geocoding() {
        openCageClient.reverse_geocoding(-2.151132, -79.825219);
    }
}