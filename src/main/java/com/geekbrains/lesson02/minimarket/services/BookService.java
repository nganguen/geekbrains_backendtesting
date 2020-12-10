package com.geekbrains.lesson02.minimarket.services;

import com.geekbrains.lesson02.minimarket.dto.BookDto;
import com.geekbrains.lesson02.minimarket.entities.Author;
import com.geekbrains.lesson02.minimarket.entities.Book;
import com.geekbrains.lesson02.minimarket.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;
    private CategoryService categoryService;
    private AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, CategoryService categoryService, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    public Optional<Book> getOneById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setPrice(bookDto.getPrice());
        book.setCategory(categoryService.findByTitle(bookDto.getCategoryTitle()).get());
        book.setAuthor(authorService.findByName(bookDto.getAuthorName()).get());
        return bookRepository.save(book);
    }

    public List<Book> getAllBookByAuthorName(String authorName) {
        Author author = authorService.findByName(authorName).get();
        return bookRepository.findByAuthor(author);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }
}
