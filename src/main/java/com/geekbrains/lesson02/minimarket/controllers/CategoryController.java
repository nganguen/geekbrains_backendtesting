package com.geekbrains.lesson02.minimarket.controllers;

import com.geekbrains.lesson02.minimarket.dto.CategoryDto;
import com.geekbrains.lesson02.minimarket.entities.Category;
import com.geekbrains.lesson02.minimarket.exceptions.ResourceNotFoundException;
import com.geekbrains.lesson02.minimarket.services.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /*@Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    } */ //т.к есть аннотация @RequiredArgsConstructor

    @GetMapping
    @ApiOperation("Returns all categories")
    public List<CategoryDto> getAllCategories() {
        List<Category> list = categoryService.getAllCaterogies();
        return list.stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getOneById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find category with id: " + id));
        return new CategoryDto(category);
    }
}
