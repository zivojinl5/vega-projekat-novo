package com.example.backend.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.core.model.Category;

public interface ICategoryService {

    List<Category> getAllCategories();

    Page<Category> getAllCategories(Pageable pageable);

    Category getCategoryById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);

}