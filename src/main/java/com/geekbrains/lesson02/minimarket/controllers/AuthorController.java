package com.geekbrains.lesson02.minimarket.controllers;

import com.geekbrains.lesson02.minimarket.dto.AuthorDto;
import com.geekbrains.lesson02.minimarket.entities.Author;
import com.geekbrains.lesson02.minimarket.exceptions.MarketError;
import com.geekbrains.lesson02.minimarket.exceptions.ResourceNotFoundException;
import com.geekbrains.lesson02.minimarket.services.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/authors")
@Api("Set of endpoints for authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    @ApiOperation("Returns all authors")
    public List<AuthorDto> getAllAuthors() {
        List<Author> list = authorService.getAllAuthors();
        return list.stream().map(AuthorDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) {
        Author author = authorService.getOneById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find author with id: " + id));
        return new AuthorDto(author);
    }

    @PostMapping
    @ApiOperation("Adds a new author. If id != null, then throw bad request response")
    public ResponseEntity<?> addNewAuthor(@RequestBody Author author) {
        if (author.getId() != null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be null for new entity"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authorService.saveNewAuthor(author), HttpStatus.CREATED);
    }

}
