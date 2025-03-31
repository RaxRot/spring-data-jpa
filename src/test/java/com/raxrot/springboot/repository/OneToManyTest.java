package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.MyTests.Address;
import com.raxrot.springboot.entity.MyTests.Order;
import com.raxrot.springboot.entity.MyTests.OrderItem;
import com.raxrot.springboot.entity.MyTests.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class OneToManyTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    private Order order;

    @BeforeEach
    public void setUp() {
        Product product = new Product();
        product.setSku("SKU123");
        product.setName("Test Product");
        product.setDescription("Awesome test product");
        product.setPrice(new BigDecimal("49.99"));
        product.setActive(true);
        product.setImageUrl("image.jpg");


        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(2);
        orderItem.setImageUrl(product.getImageUrl());


        Address address = new Address();
        address.setCity("Minsk");
        address.setStreet("Lenina");
        address.setState("MN");
        address.setCountry("Belarus");
        address.setZipCode("220000");


        order = new Order();
        order.setOrderTrackingNumber("TRACK-001");
        order.setTotalQuantity(2);
        order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(2)));
        order.setStatus("NEW");
        order.setBillingAddress(address);
        order.getOrderItems().add(orderItem);

        productRepository.save(product);
        orderRepository.save(order);
    }
    @AfterEach
    public void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void saveOrderItem() {
        assertThat(orderRepository.count()).isEqualTo(1);
        assertThat(productRepository.count()).isEqualTo(1);
    }

    @Test
    void findOrder() {
        Order foundedOrder=orderRepository.findById(1L).get();
        Set<OrderItem>items=foundedOrder.getOrderItems();
        Product product=items.iterator().next().getProduct();
        Address address=foundedOrder.getBillingAddress();
        assertThat(product.getSku()).isEqualTo("SKU123");
        assertThat(address.getStreet()).isEqualTo("Lenina");
    }

    @Test
    void updateOrder(){
        Order foundedOrder=orderRepository.findById(1L).get();
        Set<OrderItem>items=foundedOrder.getOrderItems();
        Product product=items.iterator().next().getProduct();
        Address address=foundedOrder.getBillingAddress();
        address.setCountry("Russia");
        product.setSku("SKU12345");
        productRepository.save(product);
        orderRepository.save(foundedOrder);

        Order updatedOrder=orderRepository.findById(1L).get();
        Set<OrderItem>foundedOrderItems=updatedOrder.getOrderItems();
        Product foundedProduct=foundedOrderItems.iterator().next().getProduct();
        Address updatedAddress=updatedOrder.getBillingAddress();
        assertThat(foundedProduct.getSku()).isEqualTo("SKU12345");
        assertThat(updatedAddress.getCountry()).isEqualTo("Russia");
    }

    @Test
    void deleteOrderItem() {
        Order order = orderRepository.findById(1L).orElseThrow();
        OrderItem orderItem = order.getOrderItems().iterator().next();
        Product product = orderItem.getProduct();

        orderItemRepository.delete(orderItem);
        productRepository.delete(product);

        Order updatedOrder = orderRepository.findById(1L).orElseThrow();
        assertThat(updatedOrder.getOrderItems().isEmpty()).isTrue();
    }
}
