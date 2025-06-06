package com.taxi.service.impl.find_cabs_module;

import dto.CoordinatesDTO;
import dto.TaxiDTO;
import com.taxi.mappers.TaxiMapper;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCab;
import com.taxi.service.interfaces.find_cabs_module.IFindCabsService;
import io.github.frame_code.domain.entities.Taxi;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.List;

@CommonsLog
@Service
@Setter
@RequiredArgsConstructor
public class SearchCabByDistance extends AbstractSearchCab {
    private final IFindCabsService findCabsService;
    private CoordinatesDTO coordinatesDTO;

    @Override
    public List<TaxiDTO> findCabs() {
        double minDistance = 4000;
        double MAX_DISTANCE = 6500;

        List<Taxi> nearbyCabs = List.of();
        while (minDistance <= MAX_DISTANCE) {
            nearbyCabs = findCabsService.findNearbyCabs(coordinatesDTO, minDistance);
            if(!nearbyCabs.isEmpty()) {
                log.info("Nearby cabs founded for the client " + coordinatesDTO.toString() + " with distance min: " + minDistance);
                log.info("Total cabs founded: " + nearbyCabs.size());
                break;
            }
            minDistance += 500;
        }

        if(nearbyCabs.isEmpty()) {
            log.warn("No nearby cabs founded for the client " + coordinatesDTO.toString());
        }
        return nearbyCabs.stream()
                .map(TaxiMapper.INSTANCE::toDTO)
                .toList();
    }
}
