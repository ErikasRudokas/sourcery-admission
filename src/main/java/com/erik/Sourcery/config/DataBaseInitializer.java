package com.erik.Sourcery.config;

import com.erik.Sourcery.repositories.AuthorRepository;
import com.erik.Sourcery.repositories.BookRepository;
import com.erik.Sourcery.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataBaseInitializer {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public DataBaseInitializer(BookRepository bookRepository, AuthorRepository authorRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
    }

    public boolean isDatabaseEmpty() {
        return bookRepository.count() == 0 && authorRepository.count() == 0 && reviewRepository.count() == 0;
    }

}
