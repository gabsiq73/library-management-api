package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.model.Book;
import io.github.cursodsousa.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(Book book){
        return bookRepository.save(book);
    }

}