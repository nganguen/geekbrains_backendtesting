package com.geekbrains.lesson02.minimarket.dto;

import com.geekbrains.lesson02.minimarket.entities.Book;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "Book dto in the application.")
public class BookDto {
    @ApiModelProperty(notes = "Unique identifier of the book. No two books can have the same id.", example = "1", required = true, position = 0)
    private Long id;

    @ApiModelProperty(notes = "Title of the book.", example = "Romeo and Juliet", required = true, position = 1)
    private String title;

    @ApiModelProperty(notes = "Price of the book.", example = "100", required = true, position = 2)
    private int price;

    @ApiModelProperty(notes = "Category title of the book.", example = "Book", required = true, position = 3)
    private String categoryTitle;

    @ApiModelProperty(notes = "Author of the book.", example = "William Shakespeare", required = true, position = 4)
    private String authorName;

    public BookDto(Book p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.categoryTitle = p.getCategory().getTitle();
        this.authorName = p.getAuthor().getName();
    }
}
