package com.example.bookspaceapp.controller;

import com.example.bookspaceapp.model.Review;
import com.example.bookspaceapp.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {
    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/book/{bookId}/reviews")
    public List<Review> getReviewsByBook(@PathVariable Long bookId){
        return reviewService.getReviewsByBook(bookId);
    }

    @PostMapping("/book/{bookId}/user/{userId}/review")
    public Review addReviewToBook(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody @Valid Review review) {
        return reviewService.save(bookId, userId, review);
    }

    @PutMapping("/book/{bookId}/user/{userId}/review")
    public Review updateReviewToBook(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody @Valid Review review) {
        return reviewService.update(bookId, userId, review);
    }

    @DeleteMapping("/book/{bookId}/user/{userId}/review")
    public String deleteReviewFromBook(@PathVariable Long bookId, @PathVariable Long userId) {
        reviewService.delete(bookId, userId);
        return "Review from user " + userId + " from book " + bookId + " was deleted!";
    }
}
