package com.example.backend.mapper;

import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;
import com.example.backend.data.entity.ClientEntity;
import com.example.backend.web.create_dto.ClientCreateDTO;
import com.example.backend.web.dto.ClientDTO;
import com.example.backend.web.search_dto.ClientSearchDTO;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ClientMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ClientDTO modelToDTO(Client model) {
        return modelMapper.map(model, ClientDTO.class);

    }

    public Client dTOToModel(ClientDTO dto) {
        return modelMapper.map(dto, Client.class);
    }

    public ClientEntity modelToEntity(Client model) {
        return modelMapper.map(model, ClientEntity.class);
    }

    public Client entityToModel(ClientEntity entity) {
        return modelMapper.map(entity, Client.class);
    }

    public Client createDTOToModel(ClientCreateDTO createDTO) {
        return modelMapper.map(createDTO, Client.class);
    }

    public List<Client> entitiesToModels(List<ClientEntity> entities) {
        return entities.stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());
    }

    public Page<Client> entitiesPageToModelsPage(Page<ClientEntity> entities) {
        List<Client> list = entities.getContent().stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
    }

    public ClientEntity modelToEntityForPatch(Client source, ClientEntity destination) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        // Reset skipNullEnabled to its default value
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }

    public ClientSearchModel mapSearchDTOSearchModelClient(ClientSearchDTO clientSearchDTO) {
        return modelMapper.map(clientSearchDTO, ClientSearchModel.class);

    }

    public ClientEntity mapCreateModelEntityClient(ClientCreateModel clientCreateModel) {
        return modelMapper.map(clientCreateModel, ClientEntity.class);

    }

    public ClientCreateModel createDTOToCreateModel(ClientCreateDTO createClientDTO) {
        return modelMapper.map(createClientDTO, ClientCreateModel.class);

    }

}
