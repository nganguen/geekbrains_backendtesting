package com.geekbrains.lesson02.minimarket.dto;

import com.geekbrains.lesson02.minimarket.entities.Author;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@ApiModel(description = "Author dto in the application.")
public class AuthorDto {
    @ApiModelProperty(notes = "Unique identifier of the author. No two author can have the same id.", example = "1", required = true, position = 0)
    private Long id;

    @ApiModelProperty(notes = "Author's name.", example = "William Shakespeare", required = true, position = 1)
    private String title;

    @ApiModelProperty(notes = "bookCounter", example = "1", required = false, position = 2)
    private int bookCounter;

    public AuthorDto(Author c) {
        this.id = c.getId();
        this.title = c.getName();
        this.bookCounter = c.getBooks().size();
    }
}
