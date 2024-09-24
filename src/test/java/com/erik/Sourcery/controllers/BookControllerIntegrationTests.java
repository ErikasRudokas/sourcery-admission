package com.erik.Sourcery.controllers;

import com.erik.Sourcery.TestDataUtil;
import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.domain.entities.Review;
import com.erik.Sourcery.services.AuthorService;
import com.erik.Sourcery.services.BookService;
import com.erik.Sourcery.services.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookControllerIntegrationTests {

    private final BookService bookService;

    private final AuthorService authorService;

    private final ReviewService reviewService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(BookService bookService, AuthorService authorService, ReviewService reviewService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.reviewService = reviewService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreatingABookReturns201() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        String json = objectMapper.writeValueAsString(testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBook1.getIsbn())
                        .contentType("application/json")
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatingABookReturnsBook() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        String json = objectMapper.writeValueAsString(testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBook1.getIsbn())
                        .contentType("application/json")
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBook1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBook1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publishYear").value(testBook1.getPublishYear())
        );
    }

    @Test
    public void testThatUpdatingABookReturns200() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);
        testBook1.setTitle("Updated Title");
        String json = objectMapper.writeValueAsString(testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBook1.getIsbn())
                        .contentType("application/json")
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdatingABookReturnsUpdatedBook() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);
        testBook1.setTitle("Updated Title");
        String json = objectMapper.writeValueAsString(testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBook1.getIsbn())
                        .contentType("application/json")
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBook1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBook1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.publishYear").value(testBook1.getPublishYear())
        );
    }

    @Test
    public void testThatGettingBooksReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGettingBooksReturnsBooks() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBook1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBook1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].publishYear").value(testBook1.getPublishYear())
        );
    }

    @Test
    public void testThatDeletingABookReturns204() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + testBook1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeletingABookReturns404() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/123")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGettingBooksByFilterReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/filter")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFilteringBooksByTitleReturnsBooks() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/filter?title=" + testBook1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBook1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBook1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].publishYear").value(testBook1.getPublishYear())
        );
    }

    @Test
    public void testThatFilteringByYearReturnsBooks() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/filter?year=" + testBook1.getPublishYear())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBook1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBook1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].publishYear").value(testBook1.getPublishYear())
        );
    }

    @Test
    public void testThatFilteringByAuthorNameReturnsBooks () throws Exception {
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Book testBook1 = TestDataUtil.createTestBook1(testAuthor1);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/filter?authorName=" + testAuthor1.getName())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBook1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBook1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].publishYear").value(testBook1.getPublishYear())
        );
    }

    @Test
    public void testThatFilteringByAverageRatingReturnsBooks() throws Exception{
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);
        Review testReview1 = TestDataUtil.createTestReview1(testBook1);
        reviewService.createReview(testReview1);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/filter?rating=5.0")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBook1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBook1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].publishYear").value(testBook1.getPublishYear())
        );
    }
}
