package com.geekbrains.lesson02.minimarket.controllers;

import com.geekbrains.lesson02.minimarket.dto.BookDto;
import com.geekbrains.lesson02.minimarket.entities.Book;
import com.geekbrains.lesson02.minimarket.exceptions.MarketError;
import com.geekbrains.lesson02.minimarket.exceptions.ResourceNotFoundException;
import com.geekbrains.lesson02.minimarket.services.AuthorService;
import com.geekbrains.lesson02.minimarket.services.BookService;
import com.geekbrains.lesson02.minimarket.services.CategoryService;
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
@RequestMapping("/v1/books")
@Api("Set of endpoints for books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

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
    public List<BookDto> getBooksByAuthorName(@RequestParam(name = "author_name") String authorName) {
        //List<BookDto> all = getAllBooks();
        //return all.stream().filter(book -> book.getAuthorName().equals(authorName)).collect(Collectors.toList());
        return bookService.getAllBookByAuthorName(authorName).stream().map(BookDto::new).collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Adds a new book. If id != null, then throw bad request response")
    public ResponseEntity<?> addNewBook(@RequestBody BookDto b) {
        if (b.getId() != null) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Id must be null for new entity"), HttpStatus.BAD_REQUEST);
        }
        if ((b.getTitle() == null) | (b.getPrice() == 0) | (b.getCategoryTitle() == null) | (b.getAuthorName() == null) |
                (categoryService.findByTitle(b.getCategoryTitle()).isEmpty()) | (authorService.findByName(b.getAuthorName()).isEmpty())) {
            return new ResponseEntity<>(new MarketError(HttpStatus.BAD_REQUEST.value(), "Bad request data"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BookDto(bookService.saveBook(b)), HttpStatus.CREATED);
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
