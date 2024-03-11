package com.example.backend.core.service_Implementation;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.IClientCoreRepository;
import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;
import com.example.backend.core.service.IClientService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private final IClientCoreRepository coreClientRepository;

    @Override
    public Client getClientById(Long id) {
        Client foundClient = coreClientRepository.findById(id);
        return foundClient;

    }

    @Override
    public Client createClient(ClientCreateModel clientCreateModel) {
        Client savedClient = coreClientRepository.save(clientCreateModel);
        return savedClient;
    }

    @Override
    public Client updateClient(Long id, Client client) {
        Client updatedClient = coreClientRepository.update(id, client);
        return updatedClient;

    }

    @Override
    public void deleteClient(Long id) {
        coreClientRepository.deleteById(id);
    }

    @Override
    public Page<Client> searchClients(ClientSearchModel clientSearchModel) {
        Page<Client> searchResults = coreClientRepository.searchClients(clientSearchModel);
        return searchResults;

    }

    @Override
    public HashMap<Long, String> getClientNamesByUserId(Long id) {
        return coreClientRepository.findClientNamesByUserId(id);

    }
}
