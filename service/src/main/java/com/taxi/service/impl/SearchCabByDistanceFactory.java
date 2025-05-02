package com.taxi.service.impl;

import DTO.SearchCabDTO;
import com.taxi.service.SearchCab;
import com.taxi.service.SearchCabFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("byDistance")
public class SearchCabByDistanceFactory extends SearchCabFactory {
    @Autowired
    private SearchCabByDistance searchCabByDistance;

    @Override
    public SearchCab createSearchCab(SearchCabDTO searchCabDTO) {
        searchCabByDistance.setCoordinatesDTO(searchCabDTO.coordinatesDTO());
        return searchCabByDistance;
    }
}
