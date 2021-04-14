package io.spring.bookstore.rest;

import io.spring.bookstore.model.BookQuantity;
import io.spring.bookstore.model.ShoppingCart;
import io.spring.bookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    public ShoppingCart addBookToCart(@RequestBody Long bookId){
        return shoppingCartService.addBookToShoppingCart(bookId);
    }
    @GetMapping
    public List<BookQuantity> getBooksInShoppingCart(){
        return shoppingCartService.getBooksInShoppingCart();
    }
    @PutMapping("/{bookId}/{newQuantity}")
    public ShoppingCart updateQuantity(@PathVariable Long bookId, @PathVariable BigDecimal newQuantity){
        return shoppingCartService.updateQuantity(bookId, newQuantity);
    }
    @DeleteMapping("/{bookId}")
    public ShoppingCart deleteBookFromCart(@PathVariable Long bookId){
        return shoppingCartService.deleteFromCart(bookId);
    }
    @GetMapping(value = "/mergeCarts")
    public void mergeSessionAndUserCart(){
        shoppingCartService.mergeUsersAndSessionCart();
    }
}
