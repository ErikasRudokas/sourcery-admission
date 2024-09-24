package com.erik.Sourcery.repositories;

import com.erik.Sourcery.TestDataUtil;
import com.erik.Sourcery.domain.entities.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndReturned(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        authorRepository.save(testAuthor1);
        Optional<Author> author = authorRepository.findById(testAuthor1.getId());

        assertThat(author).isPresent();
        assertThat(author.get()).isEqualTo(testAuthor1);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndReturned(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Author testAuthor2 = TestDataUtil.createTestAuthor2();
        authorRepository.save(testAuthor1);
        authorRepository.save(testAuthor2);

        Iterable<Author> authors = authorRepository.findAll();
        assertThat(authors).containsExactlyInAnyOrder(testAuthor1, testAuthor2);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        authorRepository.save(testAuthor1);
        testAuthor1.setName("Updated Name");
        authorRepository.save(testAuthor1);
        Optional<Author> author = authorRepository.findById(testAuthor1.getId());

        assertThat(author).isPresent();
        assertThat(author.get()).isEqualTo(testAuthor1);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        authorRepository.save(testAuthor1);
        authorRepository.deleteById(testAuthor1.getId());
        Optional<Author> author = authorRepository.findById(testAuthor1.getId());

        assertThat(author).isEmpty();
    }
}
