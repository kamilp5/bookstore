package io.spring.bookstore.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class BookQuantity implements Serializable,Comparable<BookQuantity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Book book;
    private BigDecimal quantity;
    private BigDecimal price;

    public BookQuantity(){}

    public void setBook(Book book){
        this.book = book;
        this.price = book.getPrice();
        this.quantity = BigDecimal.ONE;
    }

    public void update(BigDecimal quantity){
        this.quantity = quantity;
        this.price = book.getPrice().multiply(this.quantity);
    }
    public BigDecimal getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "BookQuantity{" +
                "id=" + id +
                ", book=" + book +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookQuantity that = (BookQuantity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(book, that.book) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, book, quantity, price);
    }
    @Override
    public int compareTo(BookQuantity o) {
        return book.getId().compareTo(o.getBook().getId());
    }
}
