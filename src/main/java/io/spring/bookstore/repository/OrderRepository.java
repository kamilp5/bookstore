package io.spring.bookstore.repository;

import io.spring.bookstore.model.Order;
import io.spring.bookstore.utils.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus (OrderStatus orderStatus);
}
