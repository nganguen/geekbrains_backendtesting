package com.geekbrains.lesson02.minimarket.dto;

import com.geekbrains.lesson02.minimarket.entities.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
@ApiModel(description = "Category dto in the application.")
public class CategoryDto {

    @ApiModelProperty(notes = "Unique identifier of the category. No two categories can have the same id.", example = "1", required = true, position = 0)
    private Long id;

    @ApiModelProperty(notes = "Title of the category.", example = "Food", required = true, position = 1)
    private String title;

    @ApiModelProperty(notes = "List of products in the category", example = "Bread", required = false, position = 2)
    private List<ProductDto> products;

    public CategoryDto(Category c) {
        this.id = c.getId();
        this.title = c.getTitle();
        this.products = c.getProducts().stream().map(ProductDto::new).collect(Collectors.toList());
    }
}
