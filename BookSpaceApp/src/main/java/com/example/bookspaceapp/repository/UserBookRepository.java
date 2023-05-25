package com.example.bookspaceapp.repository;

import com.example.bookspaceapp.model.Book;
import com.example.bookspaceapp.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    @Query(nativeQuery = true, value = "select * from user_book where user_id = :userId and book_id = :bookId")
    Optional<UserBook> findByUserAndBook(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query(nativeQuery = true, value = "select * from user_book where user_id = :userId and book_id = :bookId " +
            "and already_read = 'Y'")
    Optional<UserBook> findByUserAndBookRead(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query(nativeQuery = true, value = "select * from user_book where user_id = :userId and book_id = :bookId " +
            "and to_read = 'Y'")
    Optional<UserBook> findByUserAndBookToRead(@Param("userId") Long userId, @Param("bookId") Long bookId);
}
