package com.example.backend.data.adapter;

import java.util.HashMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.IClientCoreRepository;
import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;
import com.example.backend.data.entity.ClientEntity;
import com.example.backend.data.entity.CountryEntity;
import com.example.backend.data.repository.IClientJPARepository;
import com.example.backend.data.repository.ICountryJPARepository;
import com.example.backend.mapper.ClientMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ClientAdapter implements IClientCoreRepository {
    private final IClientJPARepository jpaRepository;

    private final ICountryJPARepository countryJPARepository;
    private final ClientMapper mapper;

    @Override
    public Client findById(Long id) {
        ClientEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        return mapper.entityToModel(entity);

    }

    @Override
    public Client save(ClientCreateModel clientCreateModel) {
        ClientEntity entity = mapAndPopulateClientEntity(clientCreateModel);

        ClientEntity createdEntity = jpaRepository.save(entity);
        return mapper.entityToModel(createdEntity);
    }

    @Override
    public Client update(Long id, Client model) {
        ClientEntity targetedEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        targetedEntity = mapper.modelToEntityForPatch(model,
                targetedEntity);
        ClientEntity updatedEntity = jpaRepository.save(targetedEntity);
        return mapper.entityToModel(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    private ClientEntity mapAndPopulateClientEntity(ClientCreateModel clientCreateModel) {
        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setName(clientCreateModel.getName());
        clientEntity.setAddress(clientCreateModel.getAddress());
        clientEntity.setCity(clientCreateModel.getCity());
        clientEntity.setPostalCode(clientCreateModel.getPostalCode());

        CountryEntity countryEntity = countryJPARepository.findById(clientCreateModel.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        clientEntity.setCountry(countryEntity);

        return clientEntity;
    }

    @Override
    public Page<Client> searchClients(ClientSearchModel clientSearchModel) {
        Sort sort = Sort.by(
                clientSearchModel.getSortOrder().equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                clientSearchModel.getSortField());
        Pageable pageable = PageRequest.of(clientSearchModel.getPage(), clientSearchModel.getSize(), sort);
        Page<ClientEntity> entitiesPage = jpaRepository.searchClients(clientSearchModel, pageable);

        if (entitiesPage.isEmpty()) {
            Page<ClientEntity> allEntities = jpaRepository.findAll(pageable);
            return mapper.entitiesPageToModelsPage(allEntities);

        }

        return mapper.entitiesPageToModelsPage(entitiesPage);
    }

    @Override
    public HashMap<Long, String> findClientNamesByUserId(Long id) {
        return jpaRepository.findClientNamesByUserId(id);

    }

}
