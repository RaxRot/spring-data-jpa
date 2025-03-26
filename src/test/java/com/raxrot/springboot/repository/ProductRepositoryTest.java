package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

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
    void saveMethod(){
       Product savedProduct = productRepository.save(product);

       assertThat(savedProduct).isNotNull();
       assertThat(savedProduct.getId()).isNotNull();
       assertThat(savedProduct.getId()).isGreaterThan(0);

       Optional<Product> productOptional = productRepository.findById(savedProduct.getId());
       assertThat(productOptional.isPresent());
       assertThat(productOptional.get().getId()).isEqualTo(savedProduct.getId());
    }

    @Test
    void updateMethod(){
        productRepository.save(product);
        Product productFromDb=productRepository.findById(product.getId()).get();
        productFromDb.setName("Product 2");
        productFromDb.setActive(false);
        Product savedProduct =productRepository.save(productFromDb);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
        assertThat(savedProduct.getName()).isEqualTo("Product 2");
        assertThat(savedProduct.isActive()).isFalse();
    }

    @Test
    void saveAllMethod(){
        Product product2=Product.builder()
                .name("Product 2")
                .description("Product 2 description")
                .sku("200ABC")
                .price(new BigDecimal(200))
                .active(false)
                .imageUrl("product2.jpg")
                .build();

       List<Product>products = productRepository.saveAll(List.of(product,product2));
       assertThat(products.size()).isEqualTo(2);

       products.forEach(product->{
           assertThat(product.getId()).isNotNull();
           assertThat(product.getId()).isGreaterThan(0);
       });
    }

    @Test
    void findByIdMethod(){
        productRepository.save(product);

        Optional<Product> productOptional = productRepository.findById(product.getId());

        assertThat(productOptional).isPresent();
        Product productFromDb = productOptional.get();
        assertThat(productFromDb.getId()).isEqualTo(product.getId());
        assertThat(productFromDb.getName()).isEqualTo(product.getName());
    }

    @Test
    void findAllMethod(){
        Product product2=Product.builder()
                .name("Product 2")
                .description("Product 2 description")
                .sku("200ABC")
                .price(new BigDecimal(200))
                .active(false)
                .imageUrl("product2.jpg")
                .build();

        List<Product>products = productRepository.saveAll(List.of(product,product2));

        List<Product> productsFromDb = productRepository.findAll();
        assertThat(productsFromDb.size()).isEqualTo(2);
        products.forEach(product->{
            assertThat(product.getId()).isNotNull();
            assertThat(product.getId()).isGreaterThan(0);
        });
    }

    @Test
    void deleteByIdMethod(){
        productRepository.save(product);
        productRepository.deleteById(product.getId());
        Optional<Product> productOptional = productRepository.findById(product.getId());
        assertThat(productOptional).isEmpty();
        assertThat(productRepository.findAll()).isEmpty();
    }

    @Test
    void deleteEntityMethod(){
       Product product1 = productRepository.save(product);
        productRepository.delete(product1);
        Optional<Product> productOptional = productRepository.findById(product1.getId());
        assertThat(productOptional).isEmpty();
        assertThat(productRepository.findAll()).isEmpty();
    }

    @Test
    void deleteAllMethod(){
        Product product2=Product.builder()
                .name("Product 2")
                .description("Product 2 description")
                .sku("200ABC")
                .price(new BigDecimal(200))
                .active(false)
                .imageUrl("product2.jpg")
                .build();

        List<Product>products = productRepository.saveAll(List.of(product,product2));
        productRepository.deleteAll(products);
        assertThat(productRepository.findAll()).isEmpty();
    }

    @Test
    void testCountMethod(){
        Product product2=Product.builder()
                .name("Product 2")
                .description("Product 2 description")
                .sku("200ABC")
                .price(new BigDecimal(200))
                .active(false)
                .imageUrl("product2.jpg")
                .build();
        productRepository.saveAll(List.of(product,product2));

        long count = productRepository.count();
        assertThat(count).isEqualTo(2);
    }

    @Test
    void testExistByIdMethod(){
        productRepository.save(product);
        boolean exist = productRepository.existsById(product.getId());
        assertThat(exist).isTrue();
    }


}