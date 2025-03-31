package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.MyTests.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
