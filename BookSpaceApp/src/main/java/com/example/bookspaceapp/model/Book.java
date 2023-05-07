package com.example.bookspaceapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Table(name = "book")
public class Book {

    @Id
    private String id;

    private String title;
    private String author;
    private String genre;
    private String date;

    @Lob
    @Column( name = "description")
    private String description;

}
