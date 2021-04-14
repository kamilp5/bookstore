package io.spring.bookstore.repository;

import io.spring.bookstore.model.ShoppingCart;
import io.spring.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

    ShoppingCart findByUser(User user);
}
