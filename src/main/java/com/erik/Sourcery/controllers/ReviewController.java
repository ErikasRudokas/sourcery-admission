package com.erik.Sourcery.controllers;

import com.erik.Sourcery.domain.dtos.ReviewDto;
import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.domain.entities.Review;
import com.erik.Sourcery.mappers.impl.BookMapper;
import com.erik.Sourcery.mappers.impl.ReviewMapper;
import com.erik.Sourcery.services.BookService;
import com.erik.Sourcery.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;

    private final BookService bookService;

    private final BookMapper bookMapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper, BookService bookService, BookMapper bookMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/reviews")
    public List<ReviewDto> getReviews(){
        return reviewService.getReviews().stream()
                .map(reviewMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewDto> createReview(
            @RequestParam String isbn,
            @RequestBody ReviewDto reviewDto) {
        Optional<Book> book = bookService.getBook(isbn);
        if (book.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        reviewDto.setBook(bookMapper.mapTo(book.get()));
        Review createdReview = reviewService.createReview(reviewMapper.mapFrom(reviewDto));
        return new ResponseEntity<>(reviewMapper.mapTo(createdReview), HttpStatus.CREATED);
    }
}
