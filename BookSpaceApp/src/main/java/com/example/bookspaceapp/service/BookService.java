package com.example.bookspaceapp.service;

import com.example.bookspaceapp.exception.CannotSavePhotoException;
import com.example.bookspaceapp.exception.NotFoundException;
import com.example.bookspaceapp.model.Book;
import com.example.bookspaceapp.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService {
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book){
        Book savedBook = bookRepository.save(book);
        log.info("Save new book: {}", savedBook);
        return savedBook;
    }
    
    public Book uploadCoverToBook(Long bookId, MultipartFile cover){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " was not found!"));
        try {
            book.setCover(cover.getBytes());
            log.info("Cover was uploaded for book {}!", bookId);
            return bookRepository.save(book);
        } catch (IOException e) {
            throw new CannotSavePhotoException("Book cover could not be uploaded!");
        }
    }

    public List<Book> getAll(){
        List<Book> bookList = bookRepository.findAll();
//        log.info("Retrieving all books from repository: {}", bookList);
        return bookList;
    }

    public Book getById(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " was not found!"));
        log.info("Retrieving book with id {} : {} from repository", id, book);
        return book;
    }

    public Book update(Long id, Book updatedBook){
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setGenre(updatedBook.getGenre());
            book.setDescription(updatedBook.getDescription());
            log.info("Book with id {} was updated to: {}", id, book);
            return bookRepository.save(book);
        } else {
            throw new NotFoundException("Book with id " + id + " was not found!");
        }
    }

    public void delete(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " was not found!"));
        log.info("Deleting book with id {} : {} from repository", id, book);
        bookRepository.delete(book);
    }
}
