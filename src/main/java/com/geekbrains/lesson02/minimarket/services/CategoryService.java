package com.geekbrains.lesson02.minimarket.services;

import com.geekbrains.lesson02.minimarket.entities.Category;
import com.geekbrains.lesson02.minimarket.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCaterogies() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getOneById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }
}
