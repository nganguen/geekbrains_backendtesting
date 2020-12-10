package com.geekbrains.lesson02.minimarket.controllers;

import com.geekbrains.lesson02.minimarket.dto.ProductDto;
import com.geekbrains.lesson02.minimarket.entities.Product;
import com.geekbrains.lesson02.minimarket.exceptions.MarketError;
import com.geekbrains.lesson02.minimarket.exceptions.ResourceNotFoundException;
import com.geekbrains.lesson02.minimarket.services.CategoryService;
import com.geekbrains.lesson02.minimarket.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/products")
@Api("Set of endpoints for products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    /*@Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }*/

    @GetMapping
    @ApiOperation("Returns all products")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns a specific product by their identifier. 404 if does not exist.")
    public ProductDto getProductById(@ApiParam("Id of the book to be obtained. Cannot be empty.") @PathVariable Long id) {
        Product p = productService.getOneById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find product with id: " + id)); //next to exceptionsControllerAdvice
        return new ProductDto(p);
    }

    @PostMapping
    @ApiOperation("Creates a new product. If id != null, then throw bad request response")
    public ResponseEntity<?> createNewProduct(@RequestBody ProductDto p) {
        if (p.getId() != null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be null for new entity"), HttpStatus.BAD_REQUEST);
        }
        if ((p.getTitle() == null) | (p.getPrice() == 0) | (p.getCategoryTitle() == null) |
                (p.getCategoryTitle() == null) | (categoryService.findByTitle(p.getCategoryTitle()).isEmpty())) {
            return new ResponseEntity<>(new MarketError((HttpStatus.BAD_REQUEST.value()), "Bad request data"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ProductDto(productService.saveProductDto(p)), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation("Modify product")
    public ResponseEntity<?> modifyProduct(@RequestBody ProductDto p) {
        if (p.getId() == null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be not null for new entity"), HttpStatus.BAD_REQUEST);
        }
        if (!productService.existsById(p.getId())) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Product with id: " + p.getId() + " doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        if ((p.getTitle() == null) | (p.getPrice() == 0) | (p.getCategoryTitle() == null) |
                (p.getCategoryTitle() == null) | (categoryService.findByTitle(p.getCategoryTitle()).isEmpty())) {
            return new ResponseEntity<>(new MarketError((HttpStatus.BAD_REQUEST.value()), "Bad request data"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ProductDto(productService.saveProductDto(p)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete product")
    public ResponseEntity<?> deleteById(@ApiParam("Id of the product") @PathVariable Long id) {
        if (!productService.existsById(id)) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Product with id:" + id + "doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
