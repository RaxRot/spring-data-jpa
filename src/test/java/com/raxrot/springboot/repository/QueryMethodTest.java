package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class QueryMethodTest {

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
    void findByName(){
        productRepository.save(product);
        Product foundProduct = productRepository.findByName("Product 1");
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isGreaterThan(0);
    }

    @Test
    void findByNameOrDescription(){
        productRepository.save(product);
        List<Product>products=productRepository.findByNameOrDescription("Product 2", "Product 1 description");
        assertThat(products).isNotNull();
    }

    @Test
    void findByDistinctName(){
        productRepository.save(product);
       Product produc2=Product.builder()
                .name("Product 1")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(100))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
       productRepository.save(produc2);

       Product foundProduct = productRepository.findFirstByName("Product 1");
       assertThat(foundProduct).isNotNull();
       assertThat(foundProduct.getId()).isGreaterThan(0);
    }

    @Test
    void findByPriceGreaterThan(){
        productRepository.save(product);
        Product produc2=Product.builder()
                .name("Product 1")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(200))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
        productRepository.save(produc2);

        Product founded=productRepository.findByPriceGreaterThan(BigDecimal.valueOf(150));
        assertThat(founded).isNotNull();
        assertThat(founded.getId()).isGreaterThan(0);
    }

    @Test
    void findByPriceLessThan(){
        productRepository.save(product);
        Product produc2=Product.builder()
                .name("Product 1")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(200))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
        productRepository.save(produc2);
        Product founded=productRepository.findByPriceLessThan(BigDecimal.valueOf(110));
        assertThat(founded).isNotNull();
        assertThat(founded.getId()).isGreaterThan(0);
    }

    @Test
    void findByNameContaining(){
        productRepository.save(product);
        Product produc2=Product.builder()
                .name("Product 1")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(200))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
        productRepository.save(produc2);
        List<Product>products=productRepository.findByNameContaining("odu");
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);
        assertThat(products.size()).isEqualTo(2);
    }
    @Test
    void findByNameLike(){
        productRepository.save(product);
        Product produc2=Product.builder()
                .name("Product 1")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(200))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
        productRepository.save(produc2);
        List<Product>products=productRepository.findByNameLike("%ct 1");
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void findPriceBetween(){
        productRepository.save(product);
        Product produc2=Product.builder()
                .name("Product 1")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(200))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
        productRepository.save(produc2);
        List<Product>products=productRepository.findByPriceBetween(new BigDecimal(50), new BigDecimal(2500));

        products.forEach(p-> System.out.println(p.getName() + " " + p.getPrice()));
    }
    @Test
    void findNameIn(){
        productRepository.save(product);
        Product produc2=Product.builder()
                .name("Product 2")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(200))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
        productRepository.save(produc2);
        List<Product>products=productRepository.findByNameIn(List.of("Product 1", "Product 2"));
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void findJPQLIndex(){
        productRepository.save(product);
        Product produc2=Product.builder()
                .name("Product 2")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(200))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
        productRepository.save(produc2);
        List<Product>products=productRepository.findByJPQLIndexParams("Product 1",new BigDecimal(300));
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void findJPQLNamedParams(){
        productRepository.save(product);
        Product produc2=Product.builder()
                .name("Product 2")
                .description("Product 1 description")
                .sku("100ABCD")
                .price(new BigDecimal(200))
                .active(true)
                .imageUrl("product1.jpg")
                .build();
        productRepository.save(produc2);
        List<Product>products=productRepository.findByJPQLNamedParams("Product 1",new BigDecimal(300));
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void findNativeQuery(){
        productRepository.save(product);
        List<Product>products=productRepository.findByNativeQuery("Product 1");
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(0);
        assertThat(products.size()).isEqualTo(1);
    }
}
