package com.example.bookspaceapp.repository;

import com.example.bookspaceapp.model.Review;
import com.example.bookspaceapp.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(nativeQuery = true, value = "select * from review where user_id = :userId and book_id = :bookId")
    Optional<Review> findByUserAndBook(@Param("userId") Long userId, @Param("bookId") Long bookId);

    @Query(nativeQuery = true, value = "select * from review where book_id = :bookId")
    List<Review> findByBook(@Param("bookId") Long bookId);
}
