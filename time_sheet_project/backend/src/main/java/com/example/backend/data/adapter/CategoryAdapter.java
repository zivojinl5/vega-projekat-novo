package com.example.backend.data.adapter;

import com.example.backend.core.core_repository.ICategoryCoreRepository;
import com.example.backend.core.model.Category;
import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.data.repository.ICategoryJPARepository;
import com.example.backend.mapper.CategoryMapper;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class CategoryAdapter implements ICategoryCoreRepository {

    private final ICategoryJPARepository jpaRepository;
    private final CategoryMapper mapper;

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> entityList = jpaRepository.findAll();
        return mapper.entitiesToModels(entityList);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        Page<CategoryEntity> page = jpaRepository
                .findAll(pageable);
        return mapper.entitiesPageToModelsPage(page);

    }

    @Override
    public Category findById(Long id) {
        CategoryEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return mapper.entityToModel(entity);

    }

    @Override
    public Category save(Category model) {
        CategoryEntity entity = (CategoryEntity) mapper.modelToEntity(model);
        CategoryEntity createdEntity = jpaRepository.save(entity);
        return mapper.entityToModel(createdEntity);
    }

    @Override
    public Category update(Long id, Category model) {
        if (!jpaRepository.existsById(id)) {
            return null;
        }
        CategoryEntity entity = (CategoryEntity) mapper.modelToEntity(model);
        entity.setId(id);
        CategoryEntity updatedEntity = jpaRepository.save(entity);
        return mapper.entityToModel(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);

    }

}
