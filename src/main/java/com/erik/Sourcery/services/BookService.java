package com.erik.Sourcery.services;

import com.erik.Sourcery.domain.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    boolean isExists(String isbn);

    Book createUpdateBook(String isbn, Book book);

    List<Book> getBooks();

    void deleteBook(String isbn);

    Optional<Book> getBook(String isbn);

    List<Book> getBooksByFilter(String title, Integer year, String authorName, Double rating);
}
