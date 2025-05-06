package com.taxi.service.interfaces.matcher_module;

import dto.ClientDTO;
import dto.TaxiDTO;

import java.util.List;
import java.util.Optional;

public interface IMatcherCostumerCab {
    void setCabsToNotify(List<TaxiDTO> taxiDTO);
    void setClientToMatch(ClientDTO clientDTO);
    void attach(TaxiDTO taxiDTO);
    void detach(Long id);
    Optional<TaxiDTO> notifyEachCab() throws NullPointerException;
}
