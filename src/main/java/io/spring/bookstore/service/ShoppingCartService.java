package io.spring.bookstore.service;

import io.spring.bookstore.model.Book;
import io.spring.bookstore.model.BookQuantity;
import io.spring.bookstore.model.ShoppingCart;
import io.spring.bookstore.model.User;
import io.spring.bookstore.repository.ShoppingCartRepository;
import io.spring.bookstore.utils.SessionContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private BookService bookService;
    private UserService userService;
    private ShoppingCartRepository shoppingCartRepository;
    private static final String SHOPPING_CART = "shoppingCart";

    @Autowired
    public ShoppingCartService(BookService bookService,
                               UserService userService,
                               ShoppingCartRepository shoppingCartRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart addBookToShoppingCart(Long bookId) {
        ShoppingCart shoppingCart = getShoppingCart();
        addBookToCart(shoppingCart, bookId);
        saveShoppingCart(shoppingCart);

        return shoppingCart;
    }

    public List<BookQuantity> getBooksInShoppingCart() {
        ShoppingCart shoppingCart = getShoppingCart();
        try {
            return shoppingCart.getBookQuantities().stream()
                    .sorted().collect(Collectors.toList());
        } catch (NullPointerException e) {
            System.err.println("Koszyk jest pusty");
            return null;
        }
    }

    public ShoppingCart updateQuantity(Long bookId, BigDecimal newQuantity) {
        ShoppingCart shoppingCart = getShoppingCart();
        BookQuantity found = shoppingCart.getBookQuantities().stream()
                .filter(b -> b.getBook().getId().equals(bookId)).findFirst().orElse(null);
        if (found == null) {
            return shoppingCart;
        }
        shoppingCart.getBookQuantities().remove(found);
        found.update(newQuantity);

        if (found.getQuantity().intValue() > 0) {
            shoppingCart.getBookQuantities().add(found);
        }
        saveShoppingCart(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart deleteFromCart(Long bookId) {
        ShoppingCart shoppingCart = getShoppingCart();
        for (BookQuantity b : shoppingCart.getBookQuantities()) {
            if (b.getBook().getId().equals(bookId)) {
                shoppingCart.getBookQuantities().remove(b);
                break;
            }
        }
        saveShoppingCart(shoppingCart);
        return shoppingCart;
    }

    public void mergeUsersAndSessionCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            shoppingCart = getShoppingCartInSession();
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        if (userService.isUserLogged()) {
            User user = userService.getLoggedUser();
            if (shoppingCart == null) {
                shoppingCart = new ShoppingCart();
            }
            shoppingCart.setUser(user);
            if (user.getShoppingCart() != null) {
                shoppingCart.setId(user.getShoppingCart().getId());
                for (BookQuantity book : user.getShoppingCart().getBookQuantities()) {
                    shoppingCart.getBookQuantities().add(book);
                }
            }
        }
        currentSession().removeAttribute(SHOPPING_CART);
        saveShoppingCart(shoppingCart);
    }

    public void deleteShoppingCart(ShoppingCart shoppingCart) {
        shoppingCartRepository.delete(shoppingCart);
    }

    private ShoppingCart getShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        if (userService.isUserLogged()) {
            User user = userService.getLoggedUser();
            if (user.getShoppingCart() == null) {
                shoppingCart.setUser(user);
            } else {
                shoppingCart = user.getShoppingCart();
            }
        } else {
            if (getShoppingCartInSession() != null) {
                shoppingCart = getShoppingCartInSession();
            }
        }
        return shoppingCart;
    }

    private void saveShoppingCart(ShoppingCart shoppingCart) {
        if (userService.isUserLogged()) {
            shoppingCartRepository.save(shoppingCart);
        } else {
            setShoppingCartInSession(shoppingCart);
        }
    }

    private void addBookToCart(ShoppingCart shoppingCart, Long bookId) {
        Book book = bookService.getBook(bookId);
        BookQuantity bookToAdd = new BookQuantity();
        bookToAdd.setBook(book);
        for (BookQuantity b : shoppingCart.getBookQuantities()) {
            if (b.getBook().getId().equals(book.getId())) {
                bookToAdd = b;
                bookToAdd.update(bookToAdd.getQuantity().add(BigDecimal.ONE));
                shoppingCart.getBookQuantities().remove(b);
                break;
            }
        }
        shoppingCart.getBookQuantities().add(bookToAdd);
    }

    private void setShoppingCartInSession(ShoppingCart shoppingCart) {
        currentSession().setAttribute(SHOPPING_CART, shoppingCart);
    }

    private ShoppingCart getShoppingCartInSession() {
        return (ShoppingCart) currentSession().getAttribute(SHOPPING_CART);
    }

    HttpSession currentSession() {
        return SessionContextHolder.getSession();
    }
}
