package com.geekbrains.lesson02.minimarket.repositories;

import com.geekbrains.lesson02.minimarket.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
