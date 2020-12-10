package com.geekbrains.lesson02.minimarket.services;

import com.geekbrains.lesson02.minimarket.entities.Author;
import com.geekbrains.lesson02.minimarket.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> getOneById(Long id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }
}
