package com.example.bookspaceapp.repository;

import com.example.bookspaceapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book, Long> {
    @Query(nativeQuery = true, value = "select book.* from book " +
            "join user_book on (book.id = user_book.book_id)" +
            "where user_book.user_id = :userId and user_book.already_read = 'Y' ")
    List<Book> findReadBooksByUser(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select book.* from book " +
            "join user_book on (book.id = user_book.book_id)" +
            "where user_book.user_id = :userId and user_book.to_read = 'Y' ")
    List<Book> findToReadBooksByUser(@Param("userId") Long userId);
}
