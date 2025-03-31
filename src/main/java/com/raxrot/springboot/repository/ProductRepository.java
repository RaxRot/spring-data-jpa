package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.MyTests.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

     Product findByName(String name);

    List<Product> findByNameOrDescription(String name, String description);

    Product findFirstByName(String name);

    Product findByPriceGreaterThan(BigDecimal price);
    Product findByPriceLessThan(BigDecimal price);

    List<Product> findByNameContaining(String name);
    List<Product> findByNameLike(String name);
    List<Product>findByPriceBetween(BigDecimal min, BigDecimal max);
    List<Product>findByNameIn(List<String>names);


    @Query("select p from Product p where p.name=?1 or p.price<=?2")
    List<Product>findByJPQLIndexParams(String name,BigDecimal price);

    @Query("select p from Product p where p.name = :name or p.price <= :price")
    List<Product> findByJPQLNamedParams(@Param("name") String name, @Param("price") BigDecimal price);

    @Query(value ="SELECT * FROM products WHERE name=:name" ,nativeQuery = true)
    List<Product> findByNativeQuery(@Param("name") String name);

    @Query("select p from Product p where p.sku = :sku")
    Product findBySku(@Param("sku") String sku);

    @Query("select p from Product p where p.price >= :price")
    List<Product> findByPrice(@Param("price") BigDecimal price);

    @Query("select p from Product p where p.active = true order by p.dateCreated desc")
    List<Product> findActiveProductsOrderedByDate();

    @Query("select p from Product p where lower(p.name) like lower(concat('%', :nameFragment, '%'))")
    List<Product> findByNameContainingIgnoreCase(@Param("nameFragment") String nameFragment);

    @Query("select count(p) from Product p where p.price < :price")
    Long countByPriceLessThan(@Param("price") BigDecimal price);

}
