package com.erik.Sourcery.controllers;

import com.erik.Sourcery.domain.dtos.BookDto;
import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.mappers.impl.AuthorMapper;
import com.erik.Sourcery.mappers.impl.BookMapper;
import com.erik.Sourcery.services.AuthorService;
import com.erik.Sourcery.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final BookMapper bookMapper;

    private final AuthorMapper authorMapper;

    public BookController(BookService bookService, AuthorService authorService, BookMapper bookMapper, AuthorMapper authorMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable String isbn,
                                                    @RequestBody BookDto bookDto,
                                                    @RequestParam(required = false) String authorId) {
        if(authorId != null){
            Optional<Author> author = authorService.getAuthor(Long.valueOf(authorId));
            if(author.isEmpty()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            bookDto.setAuthor(authorMapper.mapTo(author.get()));
        }
        Book book = bookMapper.mapFrom(bookDto);
        boolean exists = bookService.isExists(isbn);
        Book createdBook = bookService.createUpdateBook(isbn, book);

        if(exists){
            return new ResponseEntity<>(bookMapper.mapTo(createdBook), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(bookMapper.mapTo(createdBook), HttpStatus.CREATED);
        }
    }

    @GetMapping("/books")
    public List<BookDto> getBooks(){
        return bookService.getBooks().stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<BookDto> deleteBook(@PathVariable String isbn){
        if(bookService.isExists(isbn)){
            bookService.deleteBook(isbn);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books/filter")
    public List<BookDto> getBooksByFilter(@RequestParam(required = false) String title,
                                          @RequestParam(required = false) Integer year,
                                          @RequestParam(required = false) String authorName,
                                          @RequestParam(required = false) Double rating){
        List<Book> books = bookService.getBooksByFilter(title, year, authorName, rating);
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }
}
