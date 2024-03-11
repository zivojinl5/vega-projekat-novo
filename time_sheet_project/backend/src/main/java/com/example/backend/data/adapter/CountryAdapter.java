package com.example.backend.data.adapter;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.ICountryCoreRepository;
import com.example.backend.core.model.Country;
import com.example.backend.data.entity.CountryEntity;
import com.example.backend.data.repository.ICountryJPARepository;
import com.example.backend.mapper.CountryMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class CountryAdapter implements ICountryCoreRepository {

    private final ICountryJPARepository jpaRepository;
    private final CountryMapper mapper;

    @Override
    public List<Country> findAll() {
        List<CountryEntity> entityList = jpaRepository
                .findAll();
        return mapper.entitiesToModels(entityList);
    }

    @Override
    public Page<Country> findAll(Pageable pageable) {
        Page<CountryEntity> page = jpaRepository
                .findAll(pageable);
        return mapper.entitiesPageToModelsPage(page);

    }

    @Override
    public Country findById(Long id) {
        CountryEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));
        return (Country) mapper.entityToModel(entity);

    }

    @Override
    public Country save(Country model) {
        CountryEntity entity = (CountryEntity) mapper.modelToEntity(model);
        CountryEntity createdEntity = jpaRepository.save(entity);
        return (Country) mapper.entityToModel(createdEntity);
    }

    @Override
    public Country update(Long id, Country model) {
        if (!jpaRepository.existsById(id)) {
            return null;
        }
        CountryEntity entity = (CountryEntity) mapper.modelToEntity(model);
        entity.setId(id);
        CountryEntity updatedEntity = jpaRepository.save(entity);
        return (Country) mapper.entityToModel(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

}
