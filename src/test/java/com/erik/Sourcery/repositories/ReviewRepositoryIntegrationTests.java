package com.erik.Sourcery.repositories;

import com.erik.Sourcery.TestDataUtil;
import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.domain.entities.Review;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ReviewRepositoryIntegrationTests {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReviewRepositoryIntegrationTests(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    @Test
    public void testThatReviewCanBeCreatedAndReturned(){
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookRepository.save(testBook1);
        Review testReview1 = TestDataUtil.createTestReview1(testBook1);
        reviewRepository.save(testReview1);
        Optional<Review> review = reviewRepository.findById(testReview1.getId());

        assertThat(review).isPresent();
        assertThat(review.get()).isEqualTo(testReview1);
    }

    @Test
    public void testThatReviewCanBeUpdated(){
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookRepository.save(testBook1);
        Review testReview1 = TestDataUtil.createTestReview1(testBook1);
        reviewRepository.save(testReview1);
        testReview1.setRating(5);
        reviewRepository.save(testReview1);
        Optional<Review> review = reviewRepository.findById(testReview1.getId());

        assertThat(review).isPresent();
        assertThat(review.get()).isEqualTo(testReview1);
    }

    @Test
    public void testThatReviewCanBeDeleted(){
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookRepository.save(testBook1);
        Review testReview1 = TestDataUtil.createTestReview1(testBook1);
        reviewRepository.save(testReview1);
        reviewRepository.delete(testReview1);
        Optional<Review> review = reviewRepository.findById(testReview1.getId());
        Optional<Book> book = bookRepository.findById(testBook1.getIsbn());

        assertThat(review).isEmpty();
        assertThat(book).isPresent();
    }
}
