package com.example.bookspaceapp.controller;

import com.example.bookspaceapp.model.Book;
import com.example.bookspaceapp.service.BookService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/test")
    public  String test() {
        return  "AAAA";
    }

    @PostMapping
    public Book addBook(@RequestBody @Valid Book book){
        return bookService.save(book);
    }

    @PostMapping("/{bookId}/cover")
    public Book uploadCoverToBook(@PathVariable Long bookId, @RequestBody MultipartFile cover){
        return bookService.uploadCoverToBook(bookId, cover);
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable Long bookId){
        return bookService.getById(bookId);
    }

    @PutMapping("/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @RequestBody @Valid Book book){
        return bookService.update(bookId, book);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Long bookId){
        bookService.delete(bookId);
    }

}
