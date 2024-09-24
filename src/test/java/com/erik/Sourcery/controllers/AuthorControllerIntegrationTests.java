package com.erik.Sourcery.controllers;


import com.erik.Sourcery.TestDataUtil;
import com.erik.Sourcery.domain.dtos.AuthorDto;
import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.mappers.impl.AuthorMapper;
import com.erik.Sourcery.services.AuthorService;
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
public class AuthorControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final AuthorService authorService;

    private final ObjectMapper objectMapper;

    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService, ObjectMapper objectMapper, AuthorMapper authorMapper) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = objectMapper;
        this.authorMapper = authorMapper;
    }

    @Test
    public void testThatCreatingAuthorReturns201() throws Exception {
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        testAuthor1.setId(null);
        AuthorDto authorDto = authorMapper.mapTo(testAuthor1);

        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatingAuthorReturnsAuthor() throws Exception {
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        testAuthor1.setId(null);
        AuthorDto authorDto = authorMapper.mapTo(testAuthor1);

        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType("application/json")
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }

    @Test
    public void testThatGetAuthorsReturns200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorsReturnsCorrectAuthors() throws Exception {
        Author testAuthor1 = TestDataUtil.createTestAuthor1();
        Author testAuthor2 = TestDataUtil.createTestAuthor2();
        authorService.createAuthor(testAuthor1);
        authorService.createAuthor(testAuthor2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(testAuthor1.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(testAuthor1.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(testAuthor1.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").value(testAuthor2.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(testAuthor2.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].age").value(testAuthor2.getAge())
        );
    }
}
