package io.spring.bookstore.service;

import io.spring.bookstore.model.Author;
import io.spring.bookstore.model.Book;
import io.spring.bookstore.model.Category;
import io.spring.bookstore.model.Publisher;
import io.spring.bookstore.repository.AuthorRepository;
import io.spring.bookstore.repository.BookRepository;
import io.spring.bookstore.repository.CategoryRepository;
import io.spring.bookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    public Book addBook(Book book){
        Author author = book.getAuthor();
        Author foundAuthor = authorRepository.findByName(author.getName());
        if(foundAuthor == null){
            authorRepository.save(author);
        }else{
            book.setAuthor(foundAuthor);
        }

        Publisher publisher = book.getPublisher();
        Publisher foundPublisher = publisherRepository.findByName(publisher.getName());
        if(foundPublisher == null){
            publisherRepository.save(publisher);
        }else{
            book.setPublisher(foundPublisher);
        }
        book.setCopiesSold(0);
        bookRepository.save(book);

        return book;
    }
    public void updateBook(Book book){
        bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBook(Long id){
        return bookRepository.getOne(id);
    }
    public Book getBookById(Long id) {
        return bookRepository.getOne(id);
    }

    public List<Book> getBooksByCategory(String categoryName){
        Category category = categoryRepository.findByName(categoryName);
        return category.getBooks();
    }

    public List<Book> getBooksByAuthor(String authorName){
        Author author = authorRepository.findByName(authorName);
        return author.getBooks();
    }
    public List<Book> getBooksByPublisher(String publisherName){
        Publisher publisher = publisherRepository.findByName(publisherName);
        return publisher.getBooks();
    }
}
