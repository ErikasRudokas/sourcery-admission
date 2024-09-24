package com.erik.Sourcery.repositories;

import com.erik.Sourcery.domain.entities.Author;
import com.erik.Sourcery.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("SELECT b FROM Book b WHERE b.author.name = :authorName")
    public List<Book> findByAuthor(@Param("authorName") String authorName);

    @Query("SELECT b FROM Book b WHERE b.publishYear >= :year")
    public List<Book> findByYearPublished(@Param("year") Integer year);

    @Query("SELECT b FROM Book b JOIN Review r ON b.isbn = r.book.isbn GROUP BY b HAVING AVG(r.rating) >= :rating")
    public List<Book> findByRatingAverage(@Param("rating") Double rating);

    @Query("SELECT b FROM Book b WHERE b.title = :title")
    public List<Book> filterByTitle(@Param("title") String Title);
}
