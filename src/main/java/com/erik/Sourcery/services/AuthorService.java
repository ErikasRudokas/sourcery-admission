package com.erik.Sourcery.services;

import com.erik.Sourcery.domain.dtos.AuthorDto;
import com.erik.Sourcery.domain.entities.Author;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> getAuthors();

    Author createAuthor(Author author);

    Optional<Author> getAuthor(Long authorId);
}
