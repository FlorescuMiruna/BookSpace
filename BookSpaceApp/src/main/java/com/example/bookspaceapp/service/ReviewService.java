package com.example.bookspaceapp.service;

import com.example.bookspaceapp.exception.AlreadyExistingException;
import com.example.bookspaceapp.exception.NotFoundException;
import com.example.bookspaceapp.model.Book;
import com.example.bookspaceapp.model.Review;
import com.example.bookspaceapp.model.User;
import com.example.bookspaceapp.model.UserBook;
import com.example.bookspaceapp.repository.BookRepository;
import com.example.bookspaceapp.repository.ReviewRepository;
import com.example.bookspaceapp.repository.UserBookRepository;
import com.example.bookspaceapp.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReviewService {

    ReviewRepository reviewRepository;

    UserRepository userRepository;

    BookRepository bookRepository;

    UserBookRepository userBookRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, BookRepository bookRepository, UserBookRepository userBookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userBookRepository = userBookRepository;
    }

    public List<Review> getReviewsByBook(Long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not found!"));
        return reviewRepository.findByBook(bookId);
    }

    public Review save(Long bookId, Long userId, Review review){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " was not found!"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not found!"));

        UserBook userBook = userBookRepository.findByUserAndBookRead(userId, bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not read by user " + userId + "!"));

        Optional<Review> reviewExisting = reviewRepository.findByUserAndBook(userId, bookId);

        if (reviewExisting.isPresent()){
            throw new AlreadyExistingException("Review on book " + bookId + " from user " + userId + " was already published!");
        }

        review.setUser(user);
        review.setBook(book);
        review.setDate(LocalDate.now());

        return reviewRepository.save(review);
    }

    public Review update(Long bookId, Long userId, Review review){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " was not found!"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not found!"));

        Optional<Review> reviewExisting = reviewRepository.findByUserAndBook(userId, bookId);

        if (reviewExisting.isPresent()){
            reviewExisting.get().setReview(review.getReview());
            reviewExisting.get().setDate(LocalDate.now());
            return reviewRepository.save(reviewExisting.get());
        } else {
            throw new NotFoundException("Review on book " + bookId + " from user " + userId + " does not exist!");
        }
    }

    public void delete(Long bookId, Long userId){
        Optional<Review> reviewExisting = reviewRepository.findByUserAndBook(userId, bookId);
        if (reviewExisting.isPresent()){
            reviewRepository.deleteById(reviewExisting.get().getId());
        } else {
            throw new NotFoundException("Review on book " + bookId + " from user " + userId + " does not exist!");
        }
    }

}
