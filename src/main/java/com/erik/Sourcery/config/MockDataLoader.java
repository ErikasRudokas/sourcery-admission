package com.erik.Sourcery.config;

import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.domain.entities.Review;
import com.erik.Sourcery.repositories.AuthorRepository;
import com.erik.Sourcery.repositories.BookRepository;
import com.erik.Sourcery.repositories.ReviewRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class MockDataLoader {

    private final DataBaseInitializer dataBaseInitializer;

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final ReviewRepository reviewRepository;

    @Autowired
    public MockDataLoader(DataBaseInitializer dataBaseInitializer, BookRepository bookRepository, AuthorRepository authorRepository, ReviewRepository reviewRepository) {
        this.dataBaseInitializer = dataBaseInitializer;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void loadData() {
        if (dataBaseInitializer.isDatabaseEmpty()) {

            Author author1 = new Author(null, "John Doe", 30);
            Author author2 = new Author(null, "Jane Doe", 25);
            Author author3 = new Author(null, "Alice", 35);
            Author author4 = new Author(null, "Bob", 40);
            Author author5 = new Author(null, "Charlie", 45);
            authorRepository.save(author1);
            authorRepository.save(author2);
            authorRepository.save(author3);
            authorRepository.save(author4);
            authorRepository.save(author5);

            Book book1 = new Book("1234567890", "Book 1", 2021, author1);
            Book book2 = new Book("1234567891", "Book 2", 2020, author2);
            Book book3 = new Book("1234567892", "Book 3", 2019, author3);
            Book book4 = new Book("1234567893", "Book 4", 2018, author4);
            Book book5 = new Book("1234567894", "Book 5", 2017, author5);
            Book book6 = new Book("1234567895", "Book 6", 2016, author1);
            Book book7 = new Book("1234567896", "Book 7", 2015, author2);
            Book book8 = new Book("1234567897", "Book 8", 2014, author3);
            Book book9 = new Book("1234567898", "Book 9", 2013, author4);
            Book book10 = new Book("1234567899", "Book 10", 2012, author5);
            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
            bookRepository.save(book4);
            bookRepository.save(book5);
            bookRepository.save(book6);
            bookRepository.save(book7);
            bookRepository.save(book8);
            bookRepository.save(book9);
            bookRepository.save(book10);

            Review review1 = new Review(null, "Good book", 5, book1);
            Review review2 = new Review(null, "Bad book", 1, book2);
            Review review3 = new Review(null, "Average book", 3, book3);
            Review review4 = new Review(null, "Great book", 4, book1);
            Review review5 = new Review(null, "Terrible book", 1, book1);
            Review review6 = new Review(null, "Good book, i like it", 5, book6);
            Review review7 = new Review(null, "Bad book", 1, book7);
            Review review8 = new Review(null, "Average book", 3, book8);
            Review review9 = new Review(null, "Great book", 4, book9);
            Review review10 = new Review(null, "Terrible book", 1, book2);

            reviewRepository.save(review1);
            reviewRepository.save(review2);
            reviewRepository.save(review3);
            reviewRepository.save(review4);
            reviewRepository.save(review5);
            reviewRepository.save(review6);
            reviewRepository.save(review7);
            reviewRepository.save(review8);
            reviewRepository.save(review9);
            reviewRepository.save(review10);

            System.out.println("Mock data loaded");
        } else {
            System.out.println("Database already contains data");
        }
    }
}
