package com.geekbrains.lesson02.minimarket.controllers;

import com.geekbrains.lesson02.minimarket.dto.BookDto;
import com.geekbrains.lesson02.minimarket.entities.Book;
import com.geekbrains.lesson02.minimarket.exceptions.MarketError;
import com.geekbrains.lesson02.minimarket.exceptions.ResourceNotFoundException;
import com.geekbrains.lesson02.minimarket.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/books")
@Api("Set of endpoints for books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ApiOperation("Returns all books")
    public List<BookDto> getAllBooks() {
        List<Book> list = bookService.getAllBooks();
        return list.stream().map(BookDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns a specific book by their identifier. 404 if does not exist.")
    public BookDto getBookById(@ApiParam("Id of the book to be obtained. Cannot be empty.") @PathVariable Long id) {
        Book b = bookService.getOneById(id).orElseThrow(() -> new ResourceNotFoundException("Unable to find book with id: " + id)); //next to exceptionsControllerAdvice
        return new BookDto(b);
    }

    // http://localhost:8189/store/api/v1/books/find?author_name=Harper_Lee
    @GetMapping("/find")
    @ApiOperation("Returns all books by the author in request")
    public List<BookDto> getBooksByAuthorName(@RequestParam String author_name) {
        List<BookDto> all = getAllBooks();
        return all.stream().filter(book -> book.getAuthorName().equals(author_name)).collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Adds a new book. If id != null, then throw bad request response")
    public ResponseEntity<?> addNewBook(@RequestBody Book b) {
        if (b.getId() != null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be null for new entity"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookService.saveBook(b), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete book")
    public ResponseEntity<?> deleteById(@ApiParam("Id of the book") @PathVariable Long id) {
        if (!bookService.existsById(id)) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Book with id:" + id + "doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
