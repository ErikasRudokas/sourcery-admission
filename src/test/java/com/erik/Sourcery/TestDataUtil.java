package com.erik.Sourcery;

import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.domain.entities.Review;

public class TestDataUtil {
    private TestDataUtil() {
    }
    public static Author createTestAuthor1() {
        return Author.builder()
                .id(1L)
                .name("Erik Brown")
                .age(14)
                .build();
    }

    public static Author createTestAuthor2() {
        return Author.builder()
                .id(2L)
                .name("Erik Brownish")
                .age(42)
                .build();
    }

    public static Author createTestAuthor3() {
        return Author.builder()
                .id(3L)
                .name("Erik Brownlee")
                .age(24)
                .build();
    }

    public static Book createTestBook1(final Author author) {
        return Book.builder()
                .isbn("978-1-2345")
                .title("The first book")
                .publishYear(2021)
                .author(author)
                .build();
    }

    public static Book createTestBook2(final Author author) {
        return Book.builder()
                .isbn("978-1-2344")
                .title("The second book")
                .publishYear(2020)
                .author(author)
                .build();
    }

    public static Book createTestBook3(final Author author) {
        return Book.builder()
                .isbn("978-1-2343")
                .title("The third book")
                .publishYear(2019)
                .author(author)
                .build();
    }
    public static Review createTestReview1(final Book book) {
        return Review.builder()
                .id(1L)
                .review("This is a great book")
                .rating(5)
                .book(book)
                .build();
    }
    public static Review createTestReview2(final Book book) {
        return Review.builder()
                .id(2L)
                .review("This is a good book")
                .rating(4)
                .book(book)
                .build();
    }
    public static Review createTestReview3(final Book book) {
        return Review.builder()
                .id(3L)
                .review("This is a bad book")
                .rating(1)
                .book(book)
                .build();
    }
}
