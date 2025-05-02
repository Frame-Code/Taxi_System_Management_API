package com.taxi.service;

import DTO.TaxiDTO;

import java.util.List;

public abstract class SearchCab {
    public abstract List<TaxiDTO> findCabs();
}
