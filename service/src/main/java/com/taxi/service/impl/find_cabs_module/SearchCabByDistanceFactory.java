package com.taxi.service.impl.find_cabs_module;

import dto.SearchCabDTO;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCab;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCabFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("byDistance")
public class SearchCabByDistanceFactory extends AbstractSearchCabFactory {
    @Autowired
    private SearchCabByDistance searchCabByDistance;

    @Override
    public AbstractSearchCab createSearchCab(SearchCabDTO searchCabDTO) {
        searchCabByDistance.setCoordinatesDTO(searchCabDTO.coordinatesDTO());
        return searchCabByDistance;
    }
}
