package com.taxi.service.impl.ride_module;

import com.taxi.exceptions.ClientNotFoundException;
import com.taxi.service.interfaces.ride_module.IClientService;
import io.github.frame_code.domain.entities.Client;
import io.github.frame_code.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {
    private final ClientRepository repository;

    @Override
    public Client findToGenerateRide(Long id) {
        return repository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
    }
}
