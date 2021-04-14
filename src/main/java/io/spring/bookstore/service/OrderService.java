package io.spring.bookstore.service;

import io.spring.bookstore.model.BookQuantity;
import io.spring.bookstore.model.Order;
import io.spring.bookstore.model.ShoppingCart;
import io.spring.bookstore.model.User;
import io.spring.bookstore.repository.OrderRepository;
import io.spring.bookstore.utils.ConfirmationMailSender;
import io.spring.bookstore.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private UserService userService;
    private BookService bookService;
    private ShoppingCartService shoppingCartService;
    private ConfirmationMailSender confirmationMailSender;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        UserService userService,
                        BookService bookService,
                        ShoppingCartService shoppingCartService,
                        ConfirmationMailSender confirmationMailSender) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.shoppingCartService = shoppingCartService;
        this.confirmationMailSender = confirmationMailSender;
    }

    public Order saveOrder() {
        User user = userService.getLoggedUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        if(shoppingCart.getBookQuantities().isEmpty()){
            return null;
        }
        BigDecimal totalPrice;
        totalPrice = shoppingCart.getBookQuantities().stream()
                .map(BookQuantity::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        Order order = new Order();
        order.setUser(user);
        order.setBooksQuantities(shoppingCart.getBookQuantities());
        order.setDate(Timestamp.valueOf(LocalDateTime.now()));
        order.setOrderStatus(OrderStatus.NEW);
        order.setPrice(totalPrice);
        orderRepository.save(order);
        updateBooks(order);
        shoppingCartService.deleteShoppingCart(shoppingCart);
        confirmationMailSender.send(order);
        return order;
    }

    private void updateBooks(Order order) {
        for (BookQuantity bookQuantity : order.getBooksQuantities()) {
            bookQuantity.getBook().setCopies(bookQuantity.getBook().getCopies() - bookQuantity.getQuantity().intValue());
            bookQuantity.getBook().setCopiesSold(bookQuantity.getBook().getCopiesSold() + bookQuantity.getQuantity().intValue());
            bookService.updateBook(bookQuantity.getBook());
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.getOne(orderId);
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);

    }
}
