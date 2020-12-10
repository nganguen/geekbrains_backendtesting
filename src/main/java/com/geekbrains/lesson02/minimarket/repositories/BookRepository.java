package com.geekbrains.lesson02.minimarket.repositories;

import com.geekbrains.lesson02.minimarket.entities.Author;
import com.geekbrains.lesson02.minimarket.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(Author author);
}
