package com.erik.Sourcery.controllers;

import com.erik.Sourcery.domain.dtos.AuthorDto;
import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.mappers.impl.AuthorMapper;
import com.erik.Sourcery.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping("/authors")
    public List<AuthorDto> getAuthors() {
        return authorService.getAuthors().stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        Author author = authorMapper.mapFrom(authorDto);
        Author createdAuthor = authorService.createAuthor(author);
        return new ResponseEntity<>(authorMapper.mapTo(createdAuthor), HttpStatus.CREATED);
    }
}
