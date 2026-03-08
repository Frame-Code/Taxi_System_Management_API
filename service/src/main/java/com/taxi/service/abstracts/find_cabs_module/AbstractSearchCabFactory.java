package com.taxi.service.abstracts.find_cabs_module;

import dto.in.SearchCabDTO;

public abstract class AbstractSearchCabFactory {
    public abstract AbstractSearchCab createSearchCab(SearchCabDTO searchCabDTO);
}
