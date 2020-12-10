package com.geekbrains.lesson02.minimarket.services;

import com.geekbrains.lesson02.minimarket.dto.ProductDto;
import com.geekbrains.lesson02.minimarket.entities.Product;
import com.geekbrains.lesson02.minimarket.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getOneById(Long id) {
        return productRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product saveProductDto(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).get());
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
