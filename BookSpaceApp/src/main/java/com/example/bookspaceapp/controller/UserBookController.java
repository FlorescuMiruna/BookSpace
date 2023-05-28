package com.example.bookspaceapp.controller;

import com.example.bookspaceapp.model.Book;
import com.example.bookspaceapp.service.UserBookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserBookController {

    UserBookService userBookService;

    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @PostMapping("/book/{bookId}/user/{userId}/toRead")
    public String addBookToUserToReadList(@PathVariable Long bookId, @PathVariable Long userId){
        userBookService.addBookToUserToRead(bookId, userId);
        return "Book " + bookId + " was added to user's " + userId + " to-read list!";
    }

    @PostMapping("/book/{bookId}/user/{userId}/read")
    public Book addBookToUserReadList(@PathVariable Long bookId, @PathVariable Long userId){
         return userBookService.addBookToUserAlreadyRead(bookId, userId);

//        return "Book " + bookId + " was added to user's " + userId + " read list!";
    }

    @DeleteMapping("/book/{bookId}/user/{userId}/toRead")
    public String deleteBookFromUserToReadList(@PathVariable Long bookId, @PathVariable Long userId){
        userBookService.deleteBookFromUserToReadList(bookId, userId);
        return "Book " + bookId + " was removed from user's " + userId + " to-read list!";
    }

    @DeleteMapping("/book/{bookId}/user/{userId}/read")
    public String deleteBookFromUserReadList(@PathVariable Long bookId, @PathVariable Long userId){
        userBookService.deleteBookFromUserAlreadyReadList(bookId, userId);
        return "Book " + bookId + " was removed from user's " + userId + " read list!";
    }

    @GetMapping("/book/user/{userId}/toRead")
    public List<Book> getBookUserToReadList(@PathVariable Long userId){

        List<Book> booksToRead = userBookService.getBookUserToRead(userId);
        return booksToRead;
    }

    @GetMapping("/book/user/{userId}/read")
    public List<Book> getBookUserReadList(@PathVariable Long userId){
        List<Book> booksRead = userBookService.getBookUserRead(userId);
        return booksRead;
    }

    @PutMapping("/book/{bookId}/user/{userId}/rating/{rating}")
    public String updateBookUserRating(@PathVariable Long bookId, @PathVariable Long userId, @PathVariable Integer rating){
        if(rating > 0 & rating < 6 ) {
            userBookService.updateBookUserRating(bookId, userId, rating);
            return "User " + userId + " rated book " + bookId + " as a " + rating + "/6!";
        }
        return "Book rating must be between 0 and 6!";
    }

}
