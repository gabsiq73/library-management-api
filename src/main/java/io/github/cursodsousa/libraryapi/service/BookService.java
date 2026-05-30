package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.model.Book;
import io.github.cursodsousa.libraryapi.repository.BookRepository;
import io.github.cursodsousa.libraryapi.repository.specs.BookSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.cursodsousa.libraryapi.repository.specs.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(Book book){
        return bookRepository.save(book);
    }

    public Optional<Book> findById(UUID id){
        return bookRepository.findById(id);
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public List<Book> search(
            String isbn, String title, String authorName, String genre, Integer publicationYear){

//        Specification<Book> specs = Specification
//                .where(BookSpecs.isbnEqual(isbn))
//                .and(BookSpecs.titleLike(title))
//                .and(BookSpecs.genreEqual(genre));

        Specification<Book> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null) specs = specs.and(isbnEqual(isbn));
        if(title != null) specs = specs.and(titleLike(title));
        if(genre != null) specs = specs.and(genreEqual(genre));

        return bookRepository.findAll(BookSpecs.isbnEqual(isbn));
    }

}