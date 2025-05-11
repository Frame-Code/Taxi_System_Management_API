package com.taxi.service.impl.matcher_module;

import com.taxi.mappers.ClientMapper;
import com.taxi.mappers.TaxiMapper;
import com.taxi.service.config.TestServiceConfig;
import com.taxi.service.interfaces.matcher_module.MatchMediator;
import dto.NotificationDTO;
import io.github.frame_code.domain.repository.ClientRepository;
import io.github.frame_code.domain.repository.TaxiRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestServiceConfig.class)
class MatchMediatorImplTest {
    @Autowired
    MatchMediator matchMediator;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TaxiRepository taxiRepository;


    @Test
    void send() {
        var clientOpt = clientRepository.findById(1L);
        var taxiOpt = taxiRepository.findById(2L);

        assertNotNull(clientOpt);
        assertNotNull(taxiOpt);

        NotificationDTO notificationDTO = matchMediator.send(new NotificationDTO(
                "Request status",
                "fd",
                ClientMapper.INSTANCE.toClientDTO(clientOpt.get()),
                TaxiMapper.INSTANCE.toDTO(taxiOpt.get())));

        assertNotNull(notificationDTO);
        System.out.println(notificationDTO);
    }
}