package com.taxi.service;

import DTO.SearchCabDTO;

public abstract class SearchCabFactory {
    public abstract SearchCab createSearchCab(SearchCabDTO searchCabDTO);
}
