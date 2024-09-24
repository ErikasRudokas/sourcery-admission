package com.erik.Sourcery.controllers;


import com.erik.Sourcery.TestDataUtil;
import com.erik.Sourcery.domain.entities.Book;
import com.erik.Sourcery.domain.entities.Review;
import com.erik.Sourcery.services.BookService;
import com.erik.Sourcery.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ReviewControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final BookService bookService;

    private final ObjectMapper objectMapper;

    @Autowired
    public ReviewControllerIntegrationTests(MockMvc mockMvc, BookService bookService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatGetReviewsReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/reviews")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreatingReviewReturns201() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);
        Review testReview1 = TestDataUtil.createTestReview1(testBook1);
        String json = objectMapper.writeValueAsString(testReview1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/reviews?isbn=" + testBook1.getIsbn())
                        .contentType("application/json")
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatingReviewReturnReview() throws Exception {
        Book testBook1 = TestDataUtil.createTestBook1(null);
        bookService.createUpdateBook(testBook1.getIsbn(), testBook1);
        Review testReview1 = TestDataUtil.createTestReview1(testBook1);
        String json = objectMapper.writeValueAsString(testReview1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/reviews?isbn=" + testBook1.getIsbn())
                        .contentType("application/json")
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.review").value(testReview1.getReview())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.rating").value(testReview1.getRating())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.isbn").value(testBook1.getIsbn())
        );
    }
}
