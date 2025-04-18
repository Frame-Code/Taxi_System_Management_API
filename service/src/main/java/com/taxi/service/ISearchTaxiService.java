package com.taxi.service;

import DTO.LocationDTO;

public interface ISearchTaxiService {
    boolean isPlaceAvailable(LocationDTO locationDTO);
}
