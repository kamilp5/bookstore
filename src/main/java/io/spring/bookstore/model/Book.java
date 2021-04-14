package io.spring.bookstore.model;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.NumberFormat;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Book implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Tytuł nie może być pusty")
    private String title;
    @Column(length = 3000)
    @NotNull(message = "Opis nie może być pusty")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull(message = "Autor nie może być pusty")
    @Valid
    private Author author;
    @NotNull(message = "ISBN nie może być pusty")
    @NumberFormat
    private String isbn;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categories = new ArrayList<>();
    @NotNull(message = "Cena nie może być pusty")

    private BigDecimal coverPrice;
    @NotNull(message = "Cena nie może być pusty")

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @NotNull(message = "Wydawnictwo nie może być pusty")
    @Valid
    private Publisher publisher;
    @NotNull(message = "Liczba stron nie może być pusty")

    private Integer pages;
    @NotNull(message = "Link do okładki nie może być pusty")

    @URL(message = "URL")
    private String coverUrl;
    @NotNull(message = "Liczba kopii nie może być pusty")

    private Integer copies;


    private Integer copiesSold;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public BigDecimal getCoverPrice() {
        return coverPrice;
    }

    public void setCoverPrice(BigDecimal coverPrice) {
        this.coverPrice = coverPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getCopiesSold() {
        return copiesSold;
    }

    public void setCopiesSold(Integer copiesSold) {
        this.copiesSold = copiesSold;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", isbn='" + isbn + '\'' +
                ", categories=" + categories +
                ", coverPrice=" + coverPrice +
                ", price=" + price +
                ", publisher=" + publisher +
                ", pages=" + pages +
                ", coverUrl='" + coverUrl + '\'' +
                ", copies=" + copies +
                ", copiesSold=" + copiesSold +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(description, book.description) &&
                Objects.equals(author, book.author) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(categories, book.categories) &&
                Objects.equals(coverPrice, book.coverPrice) &&
                Objects.equals(price, book.price) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(pages, book.pages) &&
                Objects.equals(coverUrl, book.coverUrl) &&
                Objects.equals(copies, book.copies) &&
                Objects.equals(copiesSold, book.copiesSold);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, description, author, isbn, categories, coverPrice, price, publisher, pages, coverUrl, copies, copiesSold);
    }
}
