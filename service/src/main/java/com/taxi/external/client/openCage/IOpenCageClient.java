package com.taxi.external.client.openCage;

public interface IOpenCageClient {
    String reverse_geocoding(double latitude, double longitude);
}
