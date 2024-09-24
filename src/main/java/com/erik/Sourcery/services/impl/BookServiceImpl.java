package com.erik.Sourcery.services.impl;

import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.repositories.BookRepository;
import com.erik.Sourcery.services.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public Book createUpdateBook(String isbn, Book book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }

    @Override
    public Optional<Book> getBook(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public List<Book> getBooksByFilter(String title, Integer year, String authorName, Double rating) {
        if(title != null){
            return bookRepository.filterByTitle(title);
        }else if(year != null){
            return bookRepository.findByYearPublished(year);
        }
        else if(rating != null){
            return bookRepository.findByRatingAverage(rating);
        }
        else if(authorName != null){
            return bookRepository.findByAuthor(authorName);
        }
        return new ArrayList<>();
    }
}
