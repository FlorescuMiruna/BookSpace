package com.example.bookspaceapp.repository;

import com.example.bookspaceapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book,Integer> {
}
