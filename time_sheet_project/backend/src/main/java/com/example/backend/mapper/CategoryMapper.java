package com.example.backend.mapper;

import com.example.backend.core.model.Category;
import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.web.create_dto.CategoryCreateDTO;
import com.example.backend.web.dto.CategoryDTO;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CategoryMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public CategoryDTO modelToDTO(Category model) {
        return modelMapper.map(model, CategoryDTO.class);

    }

    public Category dTOToModel(CategoryDTO dto) {
        return modelMapper.map(dto, Category.class);
    }

    public CategoryEntity modelToEntity(Category model) {
        return modelMapper.map(model, CategoryEntity.class);
    }

    public Category entityToModel(CategoryEntity entity) {
        return modelMapper.map(entity, Category.class);
    }

    public Category createDTOToModel(CategoryCreateDTO createDTO) {
        return modelMapper.map(createDTO, Category.class);
    }

    public List<Category> entitiesToModels(List<CategoryEntity> entities) {
        return entities.stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());
    }

    public Page<Category> entitiesPageToModelsPage(Page<CategoryEntity> entities) {
        List<Category> list = entities.getContent().stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
    }

    public CategoryEntity modelToEntityForPatch(Category source, CategoryEntity destination) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        // Reset skipNullEnabled to its default value
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }
}
