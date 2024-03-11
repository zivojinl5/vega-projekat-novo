package com.example.backend.mapper;

import com.example.backend.core.model.Country;
import com.example.backend.data.entity.CountryEntity;
import com.example.backend.web.create_dto.CountryCreateDTO;
import com.example.backend.web.dto.CountryDTO;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CountryMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public CountryDTO modelToDTO(Country model) {
        return modelMapper.map(model, CountryDTO.class);

    }

    public Country dTOToModel(CountryDTO dto) {
        return modelMapper.map(dto, Country.class);
    }

    public CountryEntity modelToEntity(Country model) {
        return modelMapper.map(model, CountryEntity.class);
    }

    public Country entityToModel(CountryEntity entity) {
        return modelMapper.map(entity, Country.class);
    }

    public Country createDTOToModel(CountryCreateDTO createDTO) {
        return modelMapper.map(createDTO, Country.class);
    }

    public List<Country> entitiesToModels(List<CountryEntity> entities) {
        return entities.stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());
    }

    public Page<Country> entitiesPageToModelsPage(Page<CountryEntity> entities) {
        List<Country> list = entities.getContent().stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
    }

    public CountryEntity modelToEntityForPatch(Country source, CountryEntity destination) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        // Reset skipNullEnabled to its default value
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }

}
