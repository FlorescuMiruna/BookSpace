package com.example.bookspaceapp.service;

import com.example.bookspaceapp.exception.NotFoundException;
import com.example.bookspaceapp.model.Book;
import com.example.bookspaceapp.model.User;
import com.example.bookspaceapp.model.UserBook;
import com.example.bookspaceapp.repository.BookRepository;
import com.example.bookspaceapp.repository.UserBookRepository;
import com.example.bookspaceapp.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserBookService {
    UserBookRepository userBookRepository;

    UserRepository userRepository;

    BookRepository bookRepository;

    public UserBookService(UserBookRepository userBookRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void addBookToUserToRead(Long bookId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " was not found!"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not found!"));

        Optional<UserBook> optionalUserBook = userBookRepository.findByUserAndBook(userId, bookId);

        UserBook savedUserBook;

        if (optionalUserBook.isPresent()) {
            optionalUserBook.get().setToRead('Y');
            savedUserBook = userBookRepository.save(optionalUserBook.get());
        } else {
            UserBook userBook = new UserBook('N', 'Y', 0, book, user);
            savedUserBook = userBookRepository.save(userBook);
        }

        log.info("Saved new book-user To Read relation: {}", savedUserBook);

    }

    public void addBookToUserAlreadyRead(Long bookId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " was not found!"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not found!"));

        Optional<UserBook> optionalUserBook = userBookRepository.findByUserAndBook(userId, bookId);

        UserBook savedUserBook;

        if (optionalUserBook.isPresent()) {
            optionalUserBook.get().setAlreadyRead('Y');
            savedUserBook = userBookRepository.save(optionalUserBook.get());
        } else {
            UserBook userBook = new UserBook('Y', 'N', 0, book, user);
            savedUserBook = userBookRepository.save(userBook);
        }
        log.info("Saved new book-user Read relation: {}", savedUserBook);
    }

    public void deleteBookFromUserToReadList(Long bookId, Long userId){
        UserBook optionalUserBook = userBookRepository.findByUserAndBookToRead(userId, bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not found in user's " +
                        userId + " to-read list!"));
        optionalUserBook.setToRead('N');
        userBookRepository.save(optionalUserBook);
        log.info("To-Read was set to 'N' for user {} and book {}!", userId, bookId);
    }

    public void deleteBookFromUserAlreadyReadList(Long bookId, Long userId){
        UserBook optionalUserBook = userBookRepository.findByUserAndBookRead(userId, bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not found in user's " +
                        userId + " read list!"));
        optionalUserBook.setAlreadyRead('N');
        userBookRepository.save(optionalUserBook);
        log.info("Already-Read was set to 'N' for user {} and book {}!", userId, bookId);
    }

    public List<Book> getBookUserToRead(Long userId) {
        log.info("Retrieving to-read books from user {}!", userId);
        return bookRepository.findToReadBooksByUser(userId);
    }

    public List<Book> getBookUserRead(Long userId) {
        log.info("Retrieving read books from user {}!", userId);
        return bookRepository.findReadBooksByUser(userId);
    }

    public UserBook updateBookUserRating(Long bookId, Long userId, Integer rating){
        UserBook optionalUserBook = userBookRepository.findByUserAndBookRead(userId, bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not read by user " + userId + "!"));
        optionalUserBook.setRating(rating);
        log.info("Book {} rating was updated to {} by user {}!", bookId, rating, userId);
        return userBookRepository.save(optionalUserBook);
    }

}
