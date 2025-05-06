package com.taxi.service.abstracts.find_cabs_module;

import dto.TaxiDTO;

import java.util.List;

public abstract class AbstractSearchCab {
    public abstract List<TaxiDTO> findCabs();
}
