package com.example.backend.core.service_Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.ICategoryCoreRepository;
import com.example.backend.core.model.Category;
import com.example.backend.core.service.ICategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private final ICategoryCoreRepository coreCategoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> foundCategories = coreCategoryRepository.findAll();
        return foundCategories;
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        Page<Category> foundCategories = coreCategoryRepository.findAll(pageable);
        return foundCategories;

    }

    @Override
    public Category getCategoryById(Long id) {
        Category foundCategory = coreCategoryRepository.findById(id);
        return foundCategory;
    }

    @Override
    public Category createCategory(Category category) {
        Category createdCategory = coreCategoryRepository.save(category);
        return createdCategory;
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category updatedCategory = coreCategoryRepository.update(id, category);
        return updatedCategory;
    }

    @Override
    public void deleteCategory(Long id) {
        coreCategoryRepository.deleteById(id);
    }

}
