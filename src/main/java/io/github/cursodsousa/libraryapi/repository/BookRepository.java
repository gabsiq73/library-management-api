package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Author;
import io.github.cursodsousa.libraryapi.model.BookGenre;
import io.github.cursodsousa.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
* @see BookRepositoryTest
* */

public interface BookRepository extends JpaRepository<Book, UUID> {


    // Query Methods
    List<Book> findByAuthor(Author author);

    List<Book> findByTitle(String title);

    List<Book> findByIsbn(String isbn);

    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    List<Book> findByTitleOrPrice(String title, BigDecimal price);

    List<Book> findByTitleContaining(String title);

    // JPQL
    @Query("select l from Book as l order by l.title, l.price")
    List<Book> findAllOrderedByTitleAndPrice();

    @Query("select a from Book l join l.author a")
    List<Author> findAllAuthorsByBooks();

    @Query("select distinct l.title from Book l")
    List<String> findDistinctBookTitles();

    // Named Parameters
    @Query("select l from Book l where l.genre = :bookGenre order by :paramOrder")
    List<Book> findByGenre(@Param("bookGenre") BookGenre bookGenre, @Param("paramOrder") String propertyName);

    // Positional Parameters
    @Query("select l from Book l where l.genre = ?1 order by ?2")
    List<Book> findByGenrePositional(BookGenre bookGenre, String propertyName);

    @Modifying
    @Transactional
    @Query("delete from Book where genre = ?1")
    void deleteByGenre(BookGenre genre);

    @Modifying
    @Transactional
    @Query("update Book set publication_date = ?1")
    void updatePublicationDate(LocalDate date);

    boolean existsByAuthor(Author author);
}
