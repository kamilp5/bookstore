package io.spring.bookstore.repository;

import io.spring.bookstore.model.BookQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookQuantityRepository extends JpaRepository<BookQuantity,Long> {
}
