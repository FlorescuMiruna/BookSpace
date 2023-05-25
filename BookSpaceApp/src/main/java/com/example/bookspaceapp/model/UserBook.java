package com.example.bookspaceapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "user_book")
public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "already_read")
    private char alreadyRead;

    @Column(name = "to_read")
    private char toRead;

    @Column(name = "rating")
    @Max(value = 6, message = "Rating can't be more than 6!")
    @Min(value = 0, message = "Rating can't be less than 0!")
    private Integer rating;

    /** MANY TO ONE relation with Book **/
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    /** MANY TO ONE relation with User **/
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public UserBook(char alreadyRead, char toRead, Integer rating) {
        this.alreadyRead = alreadyRead;
        this.toRead = toRead;
        this.rating = rating;
    }

    public UserBook(char alreadyRead, char toRead, Integer rating, Book book, User user) {
        this.alreadyRead = alreadyRead;
        this.toRead = toRead;
        this.rating = rating;
        this.book = book;
        this.user = user;
    }
}
