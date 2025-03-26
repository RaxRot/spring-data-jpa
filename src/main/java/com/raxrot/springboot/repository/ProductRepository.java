package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Product findByName(String name);

    List<Product> findByNameOrDescription(String name, String description);

    Product findFirstByName(String name);

    Product findByPriceGreaterThan(BigDecimal price);
    Product findByPriceLessThan(BigDecimal price);

    List<Product> findByNameContaining(String name);
    List<Product> findByNameLike(String name);
    List<Product>findByPriceBetween(BigDecimal min, BigDecimal max);
    List<Product>findByNameIn(List<String>names);
}
