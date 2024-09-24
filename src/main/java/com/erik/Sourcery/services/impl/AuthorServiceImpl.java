package com.erik.Sourcery.services.impl;

import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.repositories.AuthorRepository;
import com.erik.Sourcery.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> getAuthor(Long authorId) {
        return authorRepository.findById(authorId);
    }
}
