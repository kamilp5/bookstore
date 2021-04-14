package io.spring.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.spring.bookstore.validation.ConfirmOrder;
import io.spring.bookstore.validation.Register;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(groups = Register.class, message = "Hasło nie może być puste")
    private String password;
    @Email(groups = Register.class)
    @NotEmpty(groups = Register.class, message = "Email nie może być pusty")
    private String email;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orderHistory;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @Valid
    @NotEmpty(groups = ConfirmOrder.class)
    private UserAddress userAddress;
    @ManyToMany
    private List<UserRole> roles = new ArrayList<>();
    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private ShoppingCart shoppingCart;


    public User(){}
    public User(@NotEmpty String password, @Email String email, List<Order> orderHistory, UserAddress userAddress, List<UserRole> roles) {
        this.password = password;
        this.email = email;
        this.orderHistory = orderHistory;
        this.userAddress = userAddress;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", order=" + orderHistory +
                ", userAddress=" + userAddress +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(orderHistory, user.orderHistory) &&
                Objects.equals(userAddress, user.userAddress) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(shoppingCart, user.shoppingCart);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, password, email, orderHistory, userAddress, roles, shoppingCart);
    }
}
