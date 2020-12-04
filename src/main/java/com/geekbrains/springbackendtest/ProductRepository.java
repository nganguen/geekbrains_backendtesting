package com.geekbrains.springbackendtest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // select p from Product p where p.title = ?1 and p.price = ?2
    Product findByTitleAndPrice(String title, int price);

    // select p from Product p where p.id > ?1
    List<Product> findAllByIdGreaterThan(Long minId);

    List<Product> findAllByPriceBetween(int min, int max);

    @Query("select p from Product p where p.price < 50")
    List<Product> requestAllCheapProducts();

    @Query("select p from Product p where p.price >= ?1")
    List<Product> requestProductsMinPrice(int min_price);
}
