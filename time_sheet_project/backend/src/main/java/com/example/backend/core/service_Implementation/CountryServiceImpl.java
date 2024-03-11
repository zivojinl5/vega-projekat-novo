package com.example.backend.core.service_Implementation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.ICountryCoreRepository;
import com.example.backend.core.model.Country;
import com.example.backend.core.service.ICountryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements ICountryService {

    private final ICountryCoreRepository coreCountryRepository;

    @Override
    public List<Country> getAllCountries() {
        List<Country> foundCountries = coreCountryRepository.findAll();
        return foundCountries;
    }

    @Override
    public Page<Country> getAllCountries(Pageable pageable) {
        Page<Country> foundCountries = coreCountryRepository.findAll(pageable);
        return foundCountries;

    }

    @Override
    public Country getCountryById(Long id) {
        Country foundCountry = coreCountryRepository.findById(id);
        return foundCountry;
    }

    @Override
    public Country createCountry(Country country) {
        Country savedCountry = coreCountryRepository.save(country);
        return savedCountry;
    }

    @Override
    public Country updateCountry(Long id, Country country) {
        Country updatedCountry = coreCountryRepository.update(id, country);
        return updatedCountry;
    }

    @Override
    public void deleteCountry(Long id) {
        coreCountryRepository.deleteById(id);
    }

}
