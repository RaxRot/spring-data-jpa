package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.MyTests.Address;
import com.raxrot.springboot.entity.MyTests.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class OneToOneTest {

    @Autowired
    private OrderRepository orderRepository;
    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order.setOrderTrackingNumber("100ABC");
        order.setTotalQuantity(5);
        order.setStatus("IN_PROGRESS");
        order.setTotalPrice(new BigDecimal(1000));

        Address address = new Address();
        address.setCity("Minsk");
        address.setStreet("Kanarkova");
        address.setState("Mnsk");
        address.setCountry("Belarus");
        address.setZipCode("12345");

        order.setBillingAddress(address);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void saveOrder() {
       Order savedOrder = orderRepository.save(order);
       assertThat(savedOrder).isNotNull();
       assertThat(orderRepository.count()).isEqualTo(1);
    }

    @Test
    void findOrder() {
        orderRepository.save(order);
        Order findedOrder=orderRepository.findById(1L).get();
        assertThat(findedOrder).isNotNull();
        assertThat(findedOrder.getOrderTrackingNumber()).isEqualTo(order.getOrderTrackingNumber());
    }

    @Test
    void updateOrder() {
        orderRepository.save(order);

        Order findedOrder=orderRepository.findById(1L).get();

        findedOrder.setOrderTrackingNumber("100ABC");
        Address address = findedOrder.getBillingAddress();
        address.setCity("Moscow");
        orderRepository.save(findedOrder);

        Order updatedOrder = orderRepository.findById(1L).get();
        assertThat(updatedOrder.getOrderTrackingNumber()).isEqualTo(findedOrder.getOrderTrackingNumber());
        assertThat(updatedOrder.getBillingAddress().getCity()).isEqualTo("Moscow");
    }

    @Test
    void deleteOrder() {
        orderRepository.save(order);
        orderRepository.delete(order);
        assertThat(orderRepository.count()).isEqualTo(0);
    }
}
