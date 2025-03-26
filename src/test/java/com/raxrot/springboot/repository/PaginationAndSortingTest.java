package com.raxrot.springboot.repository;


import com.raxrot.springboot.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class PaginationAndSortingTest {

    @Autowired
    private ProductRepository productRepository;
    private Product product;

    @BeforeEach
    void setUp() {
        product=Product.builder()
                .name("Product 1")
                .description("Product 1 description")
                .sku("100ABC")
                .price(new BigDecimal(100))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
    }


    @Test
    void paginationTest() {
        productRepository.save(product);

       int pageNumber=0;
       int pageSize=5;
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        Page<Product>products=productRepository.findAll(pageable);
        List<Product>productList=products.getContent();

        assertThat(pageNumber).isEqualTo(0);
        assertThat(pageSize).isEqualTo(5);
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isLessThanOrEqualTo(pageSize);
    }

    @Test
    void sortTest() {
        productRepository.save(product);
        String sortBy="price";
        List<Product>products=productRepository.findAll(Sort.by(sortBy).ascending());
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);
    }

    @Test
    void sortByMultipleFieldsTest() {
        productRepository.save(product);
        List<Product>products=
                productRepository.findAll
                        (Sort.by("price").ascending()
                                .and(Sort.by("description").descending()));
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);
    }

    @Test
    void sortAndPageTest() {
        productRepository.save(product);
        int pageNumber=0;
        int pageSize=5;
        Pageable pageable=
                PageRequest.of(pageNumber,pageSize,
                        Sort.by("price").ascending()
                                .and(Sort.by("description").descending()));

        Page<Product>products=productRepository.findAll(pageable);
        List<Product>productList=products.getContent();
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isGreaterThan(0);
    }


}
