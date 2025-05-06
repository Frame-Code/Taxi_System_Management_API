package com.taxi.service.impl.location_module;

import dto.LocationDTO;
import com.taxi.service.interfaces.location_module.IVerifyLocationService;
import io.github.frame_code.domain.entities.Province;
import io.github.frame_code.domain.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyLocationServiceImpl implements IVerifyLocationService {

    private final ProvinceRepository provinceRepository;

    /**
     * Verifies if a specific location is available.
     * This method checks if the specified province exists in the repository
     * and if that province contains the indicated city in its records.
     *
     * @param locationDTO DTO object containing the province and city names to verify
     * @return {@code true} if the location (province and city) exists in the system,
     *         {@code false} if the province doesn't exist, has no registered cities,
     *         or doesn't contain the specified city
     */
    @Override
    public boolean isLocationAvailable(LocationDTO locationDTO) {
        return provinceRepository.findProvinceByName(locationDTO.province())
                .map(Province::getCities)
                .filter(cities -> !cities.isEmpty())
                .map(cities -> cities.stream()
                        .anyMatch(city -> city.getName().equals(locationDTO.city())))
                .orElse(false);
    }
}
