package io.spring.bookstore.rest;

import io.spring.bookstore.model.*;

import io.spring.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/api/book")
    public Book saveBook(@Valid @RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping(value = "/api/book")
    public List<Book> getBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping(value = "/api/book/{bookId}")
    public Book getBookById(@PathVariable Long bookId){
        return bookService.getBookById(bookId);
    }

    @GetMapping(value = "/api/author/{authorName}")
    public List<Book> getBooksByAuthor(@PathVariable String authorName) {
        return bookService.getBooksByAuthor(authorName);
    }
    @GetMapping(value = "/api/category/{categoryName}")
    public List<Book> getBooksByCategory(@PathVariable String categoryName){
        return bookService.getBooksByCategory(categoryName);
    }
    @GetMapping(value = "/api/publisher/{publisherName}")
    public List<Book> getBooksByPublisher(@PathVariable String publisherName) {
        return bookService.getBooksByPublisher(publisherName);
    }


}
