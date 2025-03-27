package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.Product;
import com.raxrot.springboot.entity.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    private ProductCategory productCategory;

    @BeforeEach
    public void setUp() {

        productCategory = new ProductCategory();
        productCategory.setCategoryName("books");
        productCategory.setCategoryDescription("Books description");

        Product product1 = new Product();
        product1.setName("Java");
        product1.setPrice(new BigDecimal(100));
        product1.setImageUrl("https://www.google.com");
        product1.setSku("123ABC");

        Product product2 = new Product();
        product2.setName("Java2");
        product2.setPrice(new BigDecimal(200));
        product2.setImageUrl("https://www.google.com");
        product2.setSku("123ABCD");

        productCategory.getProducts().add(product1);
        productCategory.getProducts().add(product2);

        product1.setCategory(productCategory);
        product2.setCategory(productCategory);
    }
    @Test
    public void saveTest(){
        productCategoryRepository.save(productCategory);
        assertThat(productCategoryRepository.count()).isGreaterThan(0);
    }

    @Test
    @Transactional
    public void findTest(){
        productCategoryRepository.save(productCategory);
        ProductCategory productCategory=productCategoryRepository.findById(1L).get();
        assertThat(productCategory.getCategoryName()).isEqualTo("books");
        List<Product> products=productCategory.getProducts();
        assertThat(products.size()).isEqualTo(2);
        assertThat(products.get(0).getName()).isEqualTo("Java");
    }

    @Test
    @Transactional
    void updateTest(){
        productCategoryRepository.save(productCategory);
        ProductCategory productCategory=productCategoryRepository.findById(1L).get();
        productCategory.setCategoryName("books1");
        List<Product> products=productCategory.getProducts();
        products.get(0).setSku("0000000");

        ProductCategory updatedCategory= productCategoryRepository.save(productCategory);
        assertThat(updatedCategory.getCategoryName()).isEqualTo("books1");
        assertThat(updatedCategory.getProducts().get(0).getSku()).isEqualTo("0000000");
    }

    @Test
    @Transactional
    public void deleteTest(){
        ProductCategory saved = productCategoryRepository.save(productCategory);
       for (Product product : saved.getProducts()) {
           product.setCategory(null);
       }
       productCategoryRepository.delete(saved);
       assertThat(productCategoryRepository.count()).isZero();
    }
}
