package com.example.bookspaceapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotNull(message = "Book title can't be null!")
    @NotEmpty(message = "Book title can't be empty!")
    private String title;

    @Column(name = "author")
    @NotNull(message = "Book author can't be null!")
    @NotEmpty(message = "Book author can't be empty!")
    private String author;

    @Column(name = "genre")
    @NotNull(message = "Book genre can't be null!")
    @NotEmpty(message = "Book genre can't be empty!")
    private String genre;

    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Book publication date must be in the past!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Lob
    @Column(name = "description")
    @NotNull(message = "Book description can't be null!")
    @NotEmpty(message = "Book description can't be empty!")
    private String description;

    @Lob
    @Column(name = "cover", columnDefinition="BLOB")
    private byte[] cover;

    /** ONE TO MANY relation with User_Book **/
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<UserBook> userBooks = new ArrayList<>();

    /** ONE TO MANY relation with Review **/
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    public Book(Long id, String title, String author, String genre, LocalDate date, String description, List<UserBook> userBooks, List<Review> reviews) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.date = date;
        this.description = description;
        this.userBooks = userBooks;
        this.reviews = reviews;
    }
}
