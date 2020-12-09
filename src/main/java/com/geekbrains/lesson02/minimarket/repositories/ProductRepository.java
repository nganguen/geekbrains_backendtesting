package com.geekbrains.lesson02.minimarket.repositories;

import com.geekbrains.lesson02.minimarket.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
