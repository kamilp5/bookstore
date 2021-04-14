package io.spring.bookstore.model;

import io.spring.bookstore.utils.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "\"order\"")
public class Order implements Serializable, Comparable<Order> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;
    private BigDecimal price;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id_order")
    private List<BookQuantity> booksQuantities;
    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private OrderStatus orderStatus;

    public Order() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<BookQuantity> getBooksQuantities() {
        return booksQuantities;
    }

    public void setBooksQuantities(List<BookQuantity> booksQuantities) {
        this.booksQuantities = booksQuantities;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }



    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", booksQuantities=" + booksQuantities +
                ", date=" + date +
                ", user=" + user.getEmail() +
                ", orderStatus=" + orderStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(price, order.price) &&
                Objects.equals(booksQuantities, order.booksQuantities) &&
                Objects.equals(date, order.date) &&
                Objects.equals(user, order.user) &&
                orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, booksQuantities, date, user, orderStatus);
    }

    @Override
    public int compareTo(Order o) {
        return o.getId().compareTo(getId());
    }
}