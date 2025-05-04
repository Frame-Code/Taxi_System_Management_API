package com.taxi.service.interfaces;

import DTO.ClientDTO;
import DTO.TaxiDTO;

import java.util.List;
import java.util.Optional;

public interface MatcherCostumerCab {
    void setCabsToNotify(List<TaxiDTO> taxiDTO);
    void setClientToMatch(ClientDTO clientDTO);
    void attach(TaxiDTO taxiDTO);
    void detach(Long id);
    Optional<TaxiDTO> notifyEachCab() throws NullPointerException;
}
