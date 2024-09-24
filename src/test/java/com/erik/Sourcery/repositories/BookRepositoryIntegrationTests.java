package com.erik.Sourcery.repositories;

import com.erik.Sourcery.TestDataUtil;
import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.domain.entities.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class BookRepositoryIntegrationTests {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndReturned(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        bookRepository.save(testBook1);
        Optional<Book> book = bookRepository.findById(testBook1.getIsbn());

        assertThat(book).isPresent();
        assertThat(book.get()).isEqualTo(testBook1);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndReturned(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        Book testBook2 = TestDataUtil.createTestBook2(testAuthor1);
        bookRepository.save(testBook1);
        bookRepository.save(testBook2);
        Iterable<Book> books = bookRepository.findAll();

        assertThat(books).containsExactlyInAnyOrder(testBook1, testBook2);
    }

    @Test
    public void testThatBookCanBeUpdated(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        bookRepository.save(testBook1);
        testBook1.setTitle("New Title");
        bookRepository.save(testBook1);
        Optional<Book> book = bookRepository.findById(testBook1.getIsbn());

        assertThat(book).isPresent();
        assertThat(book.get()).isEqualTo(testBook1);
    }

    @Test
    public void testThatBookCanBeDeleted(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        bookRepository.save(testBook1);
        bookRepository.deleteById(testBook1.getIsbn());
        Optional<Book> book = bookRepository.findById(testBook1.getIsbn());

        assertThat(book).isEmpty();
    }

    @Test
    public void testThatBookCanBeFoundByAuthor(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        Book testBook2 = TestDataUtil.createTestBook2(testAuthor1);
        bookRepository.save(testBook1);
        bookRepository.save(testBook2);
        assertThat(bookRepository.findByAuthor(testAuthor1.getName()))
                .usingRecursiveComparison()
                .ignoringFields("reviews")
                .isEqualTo(List.of(testBook1, testBook2));
    }

    @Test
    public void testThatBookCanBeFoundByYearPublished(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        Book testBook2 = TestDataUtil.createTestBook2(testAuthor1);
        bookRepository.save(testBook1);
        bookRepository.save(testBook2);
        assertThat(bookRepository.findByYearPublished(testBook1.getPublishYear()))
                .usingRecursiveComparison()
                .ignoringFields("reviews")
                .isEqualTo(List.of(testBook1));
    }

    @Test
    public void testThatBookCanBeFoundByRatingAverage(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        Book testBook2 = TestDataUtil.createTestBook2(testAuthor1);
        Review testReview1 = TestDataUtil.createTestReview1(testBook1);
        Review testReview2 = TestDataUtil.createTestReview2(testBook1);
        Review testReview3 = TestDataUtil.createTestReview3(testBook2);

        reviewRepository.save(testReview1);
        reviewRepository.save(testReview2);
        reviewRepository.save(testReview3);

        bookRepository.save(testBook1);
        bookRepository.save(testBook2);
        assertThat(bookRepository.findByRatingAverage(4.0))
                .usingRecursiveComparison()
                .ignoringFields("reviews")
                .isEqualTo(List.of(testBook1));
    }

    @Test
    public void testThatBookCanBeFilteredByTitle(){
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        Book testBook2 = TestDataUtil.createTestBook2(testAuthor1);
        bookRepository.save(testBook1);
        bookRepository.save(testBook2);
        assertThat(bookRepository.filterByTitle(testBook1.getTitle()))
                .usingRecursiveComparison()
                .ignoringFields("reviews")
                .isEqualTo(List.of(testBook1));
    }
}
